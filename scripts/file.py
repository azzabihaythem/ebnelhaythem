import json
import requests
import ast
from requests.exceptions import RequestException
from datetime import datetime
import os
import pandas as pd
import openpyxl
import sys
import re
import argparse

def parse_arguments():
    """Parse les arguments de ligne de commande"""
    parser = argparse.ArgumentParser(
        description='Script d\'envoi de patients et génération de factures/bordereaux',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Exemples d'utilisation:
  python file.py fact2026.xlsx                    # Utilise le fichier avec les valeurs par défaut (port 8080, mois 1, année 2026)
  python file.py fact2026.xlsx --port 6868        # Change le port
  python file.py fact2026.xlsx --month 3 --year 2026 --port 8080
  python file.py fact2026.xlsx -m 3 -y 2026 -p 6868
  python file.py fact2026.xlsx --sheet "Feuil1"   # Spécifie une feuille Excel spécifique
        """
    )

    parser.add_argument(
        'excel_file',
        type=str,
        help='Chemin vers le fichier Excel (ex: fact2026.xlsx)'
    )

    parser.add_argument(
        '-m', '--month',
        type=str,
        default='1',
        help='Mois (1-12) (défaut: 1)'
    )

    parser.add_argument(
        '-y', '--year',
        type=str,
        default='2026',
        help='Année (défaut: 2026)'
    )

    parser.add_argument(
        '-p', '--port',
        type=str,
        default='8080',
        help='Port du serveur (défaut: 8080)'
    )

    parser.add_argument(
        '-s', '--sheet',
        type=str,
        default=None,
        help='Nom de la feuille Excel à utiliser (utilise la première feuille par défaut)'
    )

    parser.add_argument(
        '-o', '--output-dir',
        type=str,
        default='exports',
        help='Dossier de base pour les exports (défaut: exports)'
    )

    return parser.parse_args()

# Configuration globale (sera mise à jour par les arguments)
SERVER_PORT = "8080"
MONTH = "1"
YEAR = "2026"
EXCEL_FILE_PATH = "fact2026.xlsx"
EXCEL_SHEET_NAME = None
BASE_OUTPUT_DIR = "exports"

def update_config_from_args(args):
    """Met à jour la configuration avec les arguments"""
    global SERVER_PORT, MONTH, YEAR, EXCEL_FILE_PATH, EXCEL_SHEET_NAME, BASE_OUTPUT_DIR

    SERVER_PORT = args.port
    MONTH = args.month
    YEAR = args.year
    EXCEL_FILE_PATH = args.excel_file
    EXCEL_SHEET_NAME = args.sheet
    BASE_OUTPUT_DIR = args.output_dir

# URLs (seront reconstruites après la mise à jour de la config)
BASE_URL = None
SIGNUP_URL = None
LOGIN_URL = None
DELETE_ALL_PATIENTS_URL = None
PATIENTS_URL = None
FACTURES_URL = None
BORDERAU_URL = None
BORDERAU_TXT_URL = None

def rebuild_urls():
    """Reconstruit les URLs après modification de la configuration"""
    global BASE_URL, SIGNUP_URL, LOGIN_URL, DELETE_ALL_PATIENTS_URL, PATIENTS_URL, FACTURES_URL, BORDERAU_URL, BORDERAU_TXT_URL

    BASE_URL = f"http://localhost:{SERVER_PORT}"
    SIGNUP_URL = f"{BASE_URL}/v1/signup"
    LOGIN_URL = f"{BASE_URL}/login"
    DELETE_ALL_PATIENTS_URL = f"{BASE_URL}/v1/patients/all"
    PATIENTS_URL = f"{BASE_URL}/v1/patients/seances/month/{MONTH}/year/{YEAR}"
    FACTURES_URL = f"{BASE_URL}/v1/list/factures/month/{MONTH}/year/{YEAR}"
    BORDERAU_URL = f"{BASE_URL}/v1/borderau/month/{MONTH}/year/{YEAR}"
    BORDERAU_TXT_URL = f"{BASE_URL}/v1/txt/borderau/month/{MONTH}/year/{YEAR}"

# Mapping des colonnes Excel vers les champs attendus
COLUMN_MAPPING = {
    "absences": "absences",
    "seanceDays": "seanceDays",
    "borederauNumber": "borederauNumber",
    "factureNumber": "factureNumber",
    "AFFILE": "AFFILE",
    "DOIT": "DOIT",
    "NUMAFFILIATION": "NUMAFFILIATION",
    "DATEF_PRISE_EN_CHARGE": "DATEF_PRISE_EN_CHARGE",
    "Num_PRISE_EN_CHARGE": "Num_PRISE_EN_CHARGE",
    "DATED_PRISE_EN_CHARGE": "DATED_PRISE_EN_CHARGE",
    "firstName": "firstName",
    "lastName": "lastName",
    "typeSeanceId": "typeSeanceId"
}

# Credentials pour la création de compte
NEW_USER = {
    "active": True,
    "birthDate": "2022-02-07T10:05:32.355Z",
    "firstName": "haythem",
    "lastName": "haythem",
    "login": "haythem",
    "password": "password123",
    "roles": [
        {
            "id": 1
        }
    ],
    "clinique": {
        "id": 1
    }
}

def sanitize_filename(filename):
    """Nettoie le nom de fichier en remplaçant les caractères problématiques"""
    filename = filename.replace(' ', '_')
    filename = re.sub(r'[<>:"/\\|?*]', '_', filename)
    return filename

def create_output_directories(sheet_name):
    """Crée les dossiers de sortie pour une feuille spécifique"""
    safe_name = sanitize_filename(sheet_name)

    factures_dir = os.path.join(BASE_OUTPUT_DIR, safe_name, "factures")
    borderaux_dir = os.path.join(BASE_OUTPUT_DIR, safe_name, "borderaux")
    borderaux_txt_dir = os.path.join(BASE_OUTPUT_DIR, safe_name, "borderaux_texte")

    for directory in [factures_dir, borderaux_dir, borderaux_txt_dir]:
        if not os.path.exists(directory):
            os.makedirs(directory)
            print(f"📁 Dossier créé: {directory}")

    return factures_dir, borderaux_dir, borderaux_txt_dir

def list_excel_sheets(file_path):
    """Liste toutes les feuilles d'un fichier Excel"""
    try:
        excel_file = pd.ExcelFile(file_path)
        sheets = excel_file.sheet_names
        print(f"\n{'='*60}")
        print(f"📋 FEUILLES DISPONIBLES DANS {file_path}:")
        print(f"{'='*60}")
        for i, sheet in enumerate(sheets, 1):
            print(f"  {i}. {sheet}")
        print(f"{'='*60}")
        return sheets
    except Exception as e:
        print(f"❌ Erreur lors de la lecture du fichier Excel: {str(e)}")
        return []

def select_sheet_interactive(file_path):
    """Permet à l'utilisateur de choisir une feuille interactivement"""
    # Si une feuille a été spécifiée en paramètre, l'utiliser directement
    if EXCEL_SHEET_NAME:
        print(f"\n✅ Utilisation de la feuille spécifiée: {EXCEL_SHEET_NAME}")
        return EXCEL_SHEET_NAME

    sheets = list_excel_sheets(file_path)

    if not sheets:
        print("❌ Aucune feuille trouvée dans le fichier Excel")
        return None

    print("\n❓ Quelle feuille voulez-vous utiliser?")
    print("   Options:")
    print("   - Entrez le NUMÉRO de la feuille (ex: 1)")
    print("   - Entrez le NOM EXACT de la feuille (ex: Mars 2026)")
    print("   - Appuyez sur ENTRÉE pour utiliser la première feuille")

    choice = input("\nVotre choix: ").strip()

    if choice == "":
        print(f"✅ Utilisation de la première feuille: {sheets[0]}")
        return sheets[0]

    if choice.isdigit():
        idx = int(choice) - 1
        if 0 <= idx < len(sheets):
            print(f"✅ Feuille sélectionnée: {sheets[idx]}")
            return sheets[idx]
        else:
            print(f"⚠️ Numéro invalide. Utilisation de la première feuille: {sheets[0]}")
            return sheets[0]

    if choice in sheets:
        print(f"✅ Feuille sélectionnée: {choice}")
        return choice

    choice_lower = choice.lower()
    for sheet in sheets:
        if sheet.lower() == choice_lower:
            print(f"✅ Feuille sélectionnée: {sheet}")
            return sheet

    print(f"⚠️ '{choice}' non trouvé. Utilisation de la première feuille: {sheets[0]}")
    return sheets[0]

def print_config():
    """Affiche la configuration actuelle"""
    print(f"\n{'='*60}")
    print("⚙️ CONFIGURATION ACTUELLE")
    print(f"{'='*60}")
    print(f"📅 Mois: {MONTH}")
    print(f"📅 Année: {YEAR}")
    print(f"🔌 Port: {SERVER_PORT}")
    print(f"🌐 Base URL: {BASE_URL}")
    print(f"📂 Fichier Excel: {EXCEL_FILE_PATH}")
    if EXCEL_SHEET_NAME:
        print(f"📄 Feuille Excel: {EXCEL_SHEET_NAME}")
    else:
        print(f"📄 Feuille Excel: Non spécifiée (sera choisie interactivement)")
    print(f"📁 Dossier export: {BASE_OUTPUT_DIR}")
    print(f"{'='*60}")

def parse_string_to_list(value):
    """Convertit une string comme '["SATURDAY","THURSDAY"]' en vrai liste Python"""
    if isinstance(value, list):
        return value
    elif isinstance(value, str):
        try:
            cleaned = value.strip()
            if cleaned.startswith('"[') and cleaned.endswith(']"'):
                cleaned = cleaned[1:-1]
            result = ast.literal_eval(cleaned)
            if isinstance(result, list):
                return result
        except:
            try:
                result = json.loads(cleaned)
                if isinstance(result, list):
                    return result
            except:
                pass
    return []

def parse_absences_to_list(value):
    """Convertit les absences en liste d'entiers"""
    if isinstance(value, list):
        return [int(x) for x in value if str(x).lstrip('-').isdigit()]
    elif isinstance(value, str):
        try:
            parsed = json.loads(value)
            if isinstance(parsed, list):
                return [int(x) for x in parsed if str(x).lstrip('-').isdigit()]
        except:
            try:
                parsed = ast.literal_eval(value)
                if isinstance(parsed, list):
                    return [int(x) for x in parsed if str(x).lstrip('-').isdigit()]
            except:
                pass
        if ',' in value:
            return [int(x.strip()) for x in value.split(',') if x.strip().lstrip('-').isdigit()]
    return []

def get_value_or_null(value):
    """Retourne None (null en JSON) si la valeur est vide, sinon la valeur nettoyée"""
    if value is None or value == "" or (isinstance(value, str) and value.strip() == ""):
        return None
    if pd.isna(value):
        return None

    str_value = str(value).strip()

    if '.' in str_value:
        if str_value.endswith('.0'):
            str_value = str_value.replace('.0', '')
        elif str_value.replace('.', '').replace('-', '').isdigit():
            parts = str_value.split('.')
            if len(parts) == 2 and all(p.isdigit() for p in parts):
                return str_value

    return str_value

def clean_date(date_value):
    """Nettoie la date pour garder uniquement YYYY-MM-DD"""
    if not date_value or date_value == "" or date_value == 'nan':
        return ""

    date_str = str(date_value).strip()

    if ' ' in date_str:
        date_str = date_str.split(' ')[0]

    if hasattr(date_value, 'strftime'):
        return date_value.strftime('%Y-%m-%d')

    return date_str

def read_excel_data():
    """Lit les données depuis le fichier Excel et les convertit au format attendu"""
    print(f"\n{'='*60}")
    print("📊 LECTURE DU FICHIER EXCEL")
    print(f"{'='*60}")

    try:
        if not os.path.exists(EXCEL_FILE_PATH):
            raise FileNotFoundError(f"Fichier Excel non trouvé: {EXCEL_FILE_PATH}")

        if EXCEL_SHEET_NAME:
            df = pd.read_excel(EXCEL_FILE_PATH, sheet_name=EXCEL_SHEET_NAME)
            print(f"✅ Feuille sélectionnée: {EXCEL_SHEET_NAME}")
        else:
            df = pd.read_excel(EXCEL_FILE_PATH)
            print(f"✅ Première feuille utilisée")

        print(f"✅ Fichier Excel chargé avec succès")
        print(f"📊 Nombre de lignes: {len(df)}")
        print(f"📊 Colonnes trouvées: {list(df.columns)}")

        print(f"\n📋 APERÇU DES DONNÉES EXCEL:")
        print(df.head(5).to_string())

        patients_data = []

        for idx, row in df.iterrows():
            record = {}
            for excel_col, target_field in COLUMN_MAPPING.items():
                if excel_col in df.columns:
                    value = row[excel_col]
                    if pd.isna(value):
                        record[target_field] = ""
                    else:
                        record[target_field] = value
                else:
                    record[target_field] = ""

            patients_data.append(record)

        print(f"\n✅ {len(patients_data)} enregistrements préparés")

        global factures_dir, borderaux_dir, borderaux_txt_dir
        sheet_name_for_dir = EXCEL_SHEET_NAME if EXCEL_SHEET_NAME else "feuille_principale"
        factures_dir, borderaux_dir, borderaux_txt_dir = create_output_directories(sheet_name_for_dir)

        output_json = os.path.join(BASE_OUTPUT_DIR, sanitize_filename(sheet_name_for_dir),
                                   f"donnees_excel_preparees_{datetime.now().strftime('%Y%m%d_%H%M%S')}.json")

        # S'assurer que le dossier existe
        os.makedirs(os.path.dirname(output_json), exist_ok=True)

        with open(output_json, 'w', encoding='utf-8') as f:
            json_compatible_data = []
            for record in patients_data:
                json_record = {}
                for key, value in record.items():
                    if hasattr(value, 'strftime'):
                        json_record[key] = value.strftime('%Y-%m-%d')
                    else:
                        json_record[key] = value
                json_compatible_data.append(json_record)
            json.dump(json_compatible_data, f, indent=2, ensure_ascii=False, default=str)
        print(f"💾 Données Excel sauvegardées dans: {output_json}")

        return patients_data

    except FileNotFoundError as e:
        print(f"❌ {str(e)}")
        return []
    except Exception as e:
        print(f"🔴 Erreur lors de la lecture du fichier Excel: {str(e)}")
        import traceback
        traceback.print_exc()
        return []

def create_user():
    """Étape 1: Créer un utilisateur (signup)"""
    print(f"\n{'='*60}")
    print("📝 ÉTAPE 1: CRÉATION DE COMPTE (SIGNUP)")
    print(f"{'='*60}")
    print(f"URL: {SIGNUP_URL}")

    try:
        response = requests.post(
            url=SIGNUP_URL,
            json=NEW_USER,
            timeout=10
        )

        print(f"\n📥 RÉPONSE SIGNUP:")
        print(f"Status: {response.status_code}")

        if response.status_code in [200, 201]:
            print("✅ Compte créé avec succès (ou déjà existant)")
            return True
        else:
            print(f"❌ Échec création compte: {response.status_code}")
            return False

    except Exception as e:
        print(f"🔴 Erreur lors du signup: {str(e)}")
        return False

def login_and_get_token():
    """Étape 2: Se connecter et récupérer le token depuis le header Authorization"""
    print(f"\n{'='*60}")
    print("🔑 ÉTAPE 2: CONNEXION (LOGIN)")
    print(f"{'='*60}")

    login_payload = {
        "login": NEW_USER["login"],
        "password": NEW_USER["password"]
    }

    print(f"URL: {LOGIN_URL}")

    try:
        response = requests.post(
            url=LOGIN_URL,
            json=login_payload,
            timeout=10
        )

        print(f"\n📥 RÉPONSE LOGIN:")
        print(f"Status: {response.status_code}")

        if response.status_code == 200:
            auth_header = response.headers.get('Authorization')

            if auth_header:
                print(f"✅ Token trouvé dans le header Authorization")
                if auth_header.startswith('Bearer '):
                    return auth_header
                else:
                    return f"Bearer {auth_header}"
            else:
                print("❌ Aucun header d'autorisation trouvé")
                return None
        else:
            print(f"❌ Échec connexion: {response.status_code}")
            if response.text:
                print(f"Message: {response.text[:200]}")
            return None

    except Exception as e:
        print(f"🔴 Erreur lors du login: {str(e)}")
        return None

def delete_all_patients(token):
    """Étape 3: Supprimer tous les patients avec leurs dépendances"""
    print(f"\n{'='*60}")
    print("🗑️ ÉTAPE 3: SUPPRESSION DE TOUS LES PATIENTS (AVEC DÉPENDANCES)")
    print(f"{'='*60}")
    print(f"URL: {DELETE_ALL_PATIENTS_URL}")

    headers = {
        "Authorization": token,
        "Content-Type": "application/json"
    }

    try:
        response = requests.delete(
            url=DELETE_ALL_PATIENTS_URL,
            headers=headers,
            timeout=30
        )

        print(f"\n📥 RÉPONSE SUPPRESSION:")
        print(f"Status: {response.status_code}")

        if response.status_code in [200, 201, 204]:
            print("✅ Tous les patients (et leurs dépendances) ont été supprimés avec succès")
            if response.text:
                try:
                    data = response.json()
                    print(f"Message: {json.dumps(data, indent=2, ensure_ascii=False)}")
                except:
                    print(f"Message: {response.text}")
            return True
        else:
            print(f"⚠️ La suppression a retourné le code: {response.status_code}")
            if response.text:
                print(f"Détail: {response.text}")
            print("\n⚠️ On continue quand même l'envoi des patients...")
            return True

    except Exception as e:
        print(f"🔴 Erreur lors de la suppression des patients: {str(e)}")
        print("⚠️ On continue quand même l'envoi des patients...")
        return True

def post_patients(token, patients_data):
    """Étape 4: Poster les patients avec le token et retourner la liste des IDs réussis"""
    print(f"\n{'='*60}")
    print("🏥 ÉTAPE 4: ENVOI DES PATIENTS")
    print(f"{'='*60}")
    print(f"URL: {PATIENTS_URL}")

    headers = {
        "Authorization": token,
        "Content-Type": "application/json"
    }

    successful_patient_ids = []

    if not patients_data:
        print("❌ Aucune donnée patient à traiter")
        return []

    print(f"\n📊 Nombre total de patients à traiter: {len(patients_data)}")

    success_count = 0
    failed_count = 0

    def clean_number_string(value):
        """Nettoie les nombres comme '174.0' en '174'"""
        if not value or value == "":
            return None
        str_value = str(value).strip()
        if '.' in str_value and str_value.endswith('.0'):
            return str_value.replace('.0', '')
        return str_value

    for i, record in enumerate(patients_data, 1):
        try:
            start_date = clean_date(record.get("DATED_PRISE_EN_CHARGE", ""))
            end_date = clean_date(record.get("DATEF_PRISE_EN_CHARGE", ""))

            absences_value = record.get("absences", [])
            seance_days_value = record.get("seanceDays", [])

            if isinstance(absences_value, list):
                absences = [int(x) for x in absences_value if str(x).lstrip('-').isdigit()]
            elif isinstance(absences_value, str):
                absences = parse_absences_to_list(absences_value)
            else:
                absences = []

            if isinstance(seance_days_value, list):
                seance_days = [str(day).strip('"').upper() for day in seance_days_value]
            elif isinstance(seance_days_value, str):
                seance_days = parse_string_to_list(seance_days_value)
                seance_days = [str(day).strip('"').upper() for day in seance_days]
            else:
                seance_days = []

            seance_days = [day.replace('"', '').replace("'", "") for day in seance_days]

            borederauNumber_raw = record.get("borederauNumber", "")
            factureNumber_raw = record.get("factureNumber", "")

            borederauNumber = clean_number_string(borederauNumber_raw)
            factureNumber = clean_number_string(factureNumber_raw)

            type_seance_id = record.get("typeSeanceId", "")
            try:
                type_seance_id = int(type_seance_id) if type_seance_id else 1
            except:
                type_seance_id = 1

            patient_payload = {
                "abscence": absences,
                "borederauNumber": borederauNumber,
                "factureNumber": factureNumber,
                "patient": {
                    "affile": str(record.get("AFFILE", "")),
                    "doit": str(record.get("DOIT", "")),
                    "numAffiliation": str(record.get("NUMAFFILIATION", "")),
                    "seanceDays": seance_days,
                    "priseEnCharges": [
                        {
                            "endDate": end_date,
                            "id": 0,
                            "number": str(record.get("Num_PRISE_EN_CHARGE", "")),
                            "startDate": start_date,
                            "clinique": {
                                "id": 1
                            }
                        }
                    ],
                    "user": {
                        "lastName": str(record.get("firstName", "")),
                        "firstName": str(record.get("lastName", "")),
                        "login": str(record.get("NUMAFFILIATION", "")),
                        "password": "noPassword",
                        "updateDate": datetime.now().isoformat(),
                        "clinique": {
                            "id": 1
                        }
                    }
                },
                "typeSeanceId": type_seance_id
            }

            if i == 1:
                print(f"\n📤 EXEMPLE PAYLOAD (Patient 1):")
                print(json.dumps(patient_payload, indent=2, ensure_ascii=False))

            response = requests.post(
                url=PATIENTS_URL,
                json=patient_payload,
                headers=headers,
                timeout=10
            )

            if response.status_code == 201:
                print(f"  ✅ Patient {i} réussi")
                success_count += 1
                try:
                    response_data = response.json()
                    patient_id = response_data.get('id') or response_data.get('patientId') or str(i)
                    successful_patient_ids.append(str(patient_id))
                except:
                    successful_patient_ids.append(str(i))
            else:
                print(f"  ❌ Patient {i} échoué (status: {response.status_code})")
                if response.text:
                    print(f"     Message: {response.text[:200]}")
                failed_count += 1

        except Exception as e:
            print(f"  ⚠️ Erreur patient {i}: {str(e)}")
            failed_count += 1

    print(f"\n{'='*60}")
    print(f"📊 RÉSUMÉ PATIENTS")
    print(f"{'='*60}")
    print(f"Total: {len(patients_data)}")
    print(f"✅ Succès: {success_count}")
    print(f"❌ Échecs: {failed_count}")
    print(f"🆔 IDs des patients réussis: {successful_patient_ids}")

    return successful_patient_ids

def generate_factures(token, patient_ids):
    """Étape 5: Générer les factures pour les patients réussis"""
    print(f"\n{'='*60}")
    print("📄 ÉTAPE 5: GÉNÉRATION DES FACTURES")
    print(f"{'='*60}")

    if not patient_ids:
        print("❌ Aucun ID de patient à traiter")
        return

    headers = {
        "Authorization": token,
        "Content-Type": "application/json"
    }

    print(f"URL: {FACTURES_URL}")
    print(f"Nombre de patients à facturer: {len(patient_ids)}")

    try:
        response = requests.post(
            url=FACTURES_URL,
            json=patient_ids,
            headers=headers,
            timeout=30
        )

        print(f"\n📥 RÉPONSE GÉNÉRATION FACTURES:")
        print(f"Status: {response.status_code}")

        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

        if response.status_code in [200, 201]:
            print("✅ Factures générées avec succès")
            content_type = response.headers.get('Content-Type', '').lower()

            if 'application/pdf' in content_type or response.content[:4] == b'%PDF':
                filename = os.path.join(factures_dir, f"factures_{timestamp}.pdf")
                with open(filename, 'wb') as f:
                    f.write(response.content)
                print(f"💾 Factures PDF sauvegardées dans: {filename}")
            else:
                filename = os.path.join(factures_dir, f"factures_{timestamp}.txt")
                with open(filename, 'w', encoding='utf-8') as f:
                    f.write(response.text)
                print(f"💾 Réponse sauvegardée dans: {filename}")
        else:
            print(f"❌ Échec génération factures: {response.status_code}")
            filename = os.path.join(factures_dir, f"factures_{timestamp}_error.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(f"Status: {response.status_code}\n\n")
                f.write(response.text)
            print(f"💾 Erreur sauvegardée dans: {filename}")

    except Exception as e:
        print(f"🔴 Erreur lors de la génération des factures: {str(e)}")

def generate_borderaux(token, patient_ids):
    """Étape 6: Générer les bordereaux pour les patients réussis"""
    print(f"\n{'='*60}")
    print("📋 ÉTAPE 6: GÉNÉRATION DES BORDEREAUX")
    print(f"{'='*60}")

    if not patient_ids:
        print("❌ Aucun ID de patient à traiter")
        return

    headers = {
        "Authorization": token,
        "Content-Type": "application/json"
    }

    print(f"URL: {BORDERAU_URL}")
    print(f"Nombre de patients: {len(patient_ids)}")

    try:
        response = requests.post(
            url=BORDERAU_URL,
            json=patient_ids,
            headers=headers,
            timeout=30
        )

        print(f"\n📥 RÉPONSE GÉNÉRATION BORDEREAUX:")
        print(f"Status: {response.status_code}")

        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

        if response.status_code in [200, 201]:
            print("✅ Bordereaux générés avec succès")
            content_type = response.headers.get('Content-Type', '').lower()

            if 'application/pdf' in content_type or response.content[:4] == b'%PDF':
                filename = os.path.join(borderaux_dir, f"borderaux_{timestamp}.pdf")
                with open(filename, 'wb') as f:
                    f.write(response.content)
                print(f"💾 Bordereaux PDF sauvegardés dans: {filename}")
            else:
                filename = os.path.join(borderaux_dir, f"borderaux_{timestamp}.txt")
                with open(filename, 'w', encoding='utf-8') as f:
                    f.write(response.text)
                print(f"💾 Réponse sauvegardée dans: {filename}")
        else:
            print(f"❌ Échec génération bordereaux: {response.status_code}")
            filename = os.path.join(borderaux_dir, f"borderaux_{timestamp}_error.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(f"Status: {response.status_code}\n\n")
                f.write(response.text)
            print(f"💾 Erreur sauvegardée dans: {filename}")

    except Exception as e:
        print(f"🔴 Erreur lors de la génération des bordereaux: {str(e)}")

def generate_borderaux_texte(token, patient_ids):
    """Étape 7: Générer les bordereaux texte"""
    print(f"\n{'='*60}")
    print("📄 ÉTAPE 7: GÉNÉRATION DES BORDEREAUX TEXTE")
    print(f"{'='*60}")

    if not patient_ids:
        print("❌ Aucun ID de patient à traiter")
        return

    headers = {
        "Authorization": token,
        "Content-Type": "application/json"
    }

    print(f"URL: {BORDERAU_TXT_URL}")
    print(f"Nombre de patients: {len(patient_ids)}")

    try:
        response = requests.get(
            url=BORDERAU_TXT_URL,
            json=patient_ids,
            headers=headers,
            timeout=30
        )

        print(f"\n📥 RÉPONSE BORDEREAUX TEXTE:")
        print(f"Status: {response.status_code}")

        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

        if response.status_code in [200, 201]:
            print("✅ Bordereaux texte générés avec succès")
            filename = os.path.join(borderaux_txt_dir, f"borderaux_texte_{timestamp}.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(response.text)
            print(f"💾 Bordereaux texte sauvegardés dans: {filename}")
            if len(response.text) < 1000:
                print(f"📋 Contenu: {response.text}")
            else:
                print(f"📋 Aperçu: {response.text[:500]}...")
        else:
            print(f"❌ Échec génération bordereaux texte: {response.status_code}")
            filename = os.path.join(borderaux_txt_dir, f"borderaux_texte_{timestamp}_error.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(f"Status: {response.status_code}\n\n")
                f.write(response.text)
            print(f"💾 Erreur sauvegardée dans: {filename}")

    except Exception as e:
        print(f"🔴 Erreur lors de la génération des bordereaux texte: {str(e)}")

    """Étape 7: Générer les bordereaux texte"""
    print(f"\n{'='*60}")
    print("📄 ÉTAPE 7: GÉNÉRATION DES BORDEREAUX TEXTE")
    print(f"{'='*60}")

    if not patient_ids:
        print("❌ Aucun ID de patient à traiter")
        return

    headers = {
        "Authorization": token
    }

    print(f"URL: {BORDERAU_TXT_URL}")
    print(f"Nombre de patients: {len(patient_ids)}")
    print(f"IDs: {patient_ids}")

    try:
        # Correction : GET sans body JSON, comme dans file9.py
        response = requests.get(
            url=BORDERAU_TXT_URL,
            headers=headers,
            timeout=30
        )

        print(f"\n📥 RÉPONSE BORDEREAUX TEXTE:")
        print(f"Status: {response.status_code}")

        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

        if response.status_code in [200, 201]:
            print("✅ Bordereaux texte générés avec succès")
            filename = os.path.join(borderaux_txt_dir, f"borderaux_texte_{timestamp}.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(response.text)
            print(f"💾 Bordereaux texte sauvegardés dans: {filename}")
            if len(response.text) < 1000:
                print(f"📋 Contenu: {response.text}")
            else:
                print(f"📋 Aperçu: {response.text[:500]}...")
        else:
            print(f"❌ Échec génération bordereaux texte: {response.status_code}")
            filename = os.path.join(borderaux_txt_dir, f"borderaux_texte_{timestamp}_error.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(f"Status: {response.status_code}\n\n")
                f.write(response.text)
            print(f"💾 Erreur sauvegardée dans: {filename}")

    except Exception as e:
        print(f"🔴 Erreur lors de la génération des bordereaux texte: {str(e)}")
    """Étape 7: Générer les bordereaux texte"""
    print(f"\n{'='*60}")
    print("📄 ÉTAPE 7: GÉNÉRATION DES BORDEREAUX TEXTE")
    print(f"{'='*60}")

    if not patient_ids:
        print("❌ Aucun ID de patient à traiter")
        return

    headers = {
        "Authorization": token,
        "Content-Type": "application/json"
    }

    print(f"URL: {BORDERAU_TXT_URL}")
    print(f"Nombre de patients: {len(patient_ids)}")

    try:
        response = requests.get(
            url=BORDERAU_TXT_URL,
            json=patient_ids,
            headers=headers,
            timeout=30
        )

        print(f"\n📥 RÉPONSE BORDEREAUX TEXTE:")
        print(f"Status: {response.status_code}")

        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

        if response.status_code in [200, 201]:
            print("✅ Bordereaux texte générés avec succès")
            filename = os.path.join(borderaux_txt_dir, f"borderaux_texte_{timestamp}.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(response.text)
            print(f"💾 Bordereaux texte sauvegardés dans: {filename}")
            if len(response.text) < 1000:
                print(f"📋 Contenu: {response.text}")
            else:
                print(f"📋 Aperçu: {response.text[:500]}...")
        else:
            print(f"❌ Échec génération bordereaux texte: {response.status_code}")
            filename = os.path.join(borderaux_txt_dir, f"borderaux_texte_{timestamp}_error.txt")
            with open(filename, 'w', encoding='utf-8') as f:
                f.write(f"Status: {response.status_code}\n\n")
                f.write(response.text)
            print(f"💾 Erreur sauvegardée dans: {filename}")

    except Exception as e:
        print(f"🔴 Erreur lors de la génération des bordereaux texte: {str(e)}")

def main():
    """Fonction principale"""
    global EXCEL_SHEET_NAME, factures_dir, borderaux_dir, borderaux_txt_dir

    # Parser les arguments
    args = parse_arguments()

    # Mettre à jour la configuration
    update_config_from_args(args)

    # Reconstruire les URLs avec la nouvelle configuration
    rebuild_urls()

    print("\n" + "="*60)
    print("🚀 DÉBUT DU PROCESSUS COMPLET (7 ÉTAPES)")
    print("="*60)

    if not os.path.exists(EXCEL_FILE_PATH):
        print(f"❌ Fichier Excel non trouvé: {EXCEL_FILE_PATH}")
        print("Veuillez vérifier le chemin du fichier.")
        return

    # Sélectionner la feuille Excel (interactive ou par paramètre)
    EXCEL_SHEET_NAME = select_sheet_interactive(EXCEL_FILE_PATH)
    if not EXCEL_SHEET_NAME:
        print("❌ Impossible de sélectionner une feuille. Arrêt du processus.")
        return

    print_config()

    patients_data = read_excel_data()
    if not patients_data:
        print("❌ Impossible de lire les données Excel. Arrêt du processus.")
        return

    print(f"\n⚠️ Voulez-vous continuer avec l'envoi de ces {len(patients_data)} patients? (o/n): ", end="")
    choix = input().strip().lower()
    if choix != 'o':
        print("❌ Processus arrêté")
        return

    # Étape 1: Créer utilisateur
    if not create_user():
        print("\n⚠️ Continuer quand même? (o/n): ", end="")
        choix = input().strip().lower()
        if choix != 'o':
            print("❌ Processus arrêté")
            return

    # Étape 2: Login
    token = login_and_get_token()
    if not token:
        print("❌ Impossible d'obtenir le token")
        return

    print(f"\n✅ Token obtenu avec succès")

    # Étape 3: Supprimer tous les patients existants (avec dépendances)
    delete_all_patients(token)

    # Étape 4: Poster patients
    successful_ids = post_patients(token, patients_data)

    if not successful_ids:
        print("\n❌ Aucun patient posté avec succès")
        return

    # Étape 5: Générer factures
    generate_factures(token, successful_ids)

    # Étape 6: Générer bordereaux
    generate_borderaux(token, successful_ids)

    # Étape 7: Générer bordereaux texte
    generate_borderaux_texte(token, successful_ids)

    print(f"\n{'='*60}")
    print("✅ PROCESSUS TERMINÉ")
    print(f"📄 Feuille traitée: {EXCEL_SHEET_NAME}")
    print(f"📁 Dossier de sortie: {os.path.join(BASE_OUTPUT_DIR, sanitize_filename(EXCEL_SHEET_NAME))}")
    print(f"{'='*60}")

if __name__ == "__main__":
    main()