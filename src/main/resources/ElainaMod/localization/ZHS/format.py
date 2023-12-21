import sys
import re
import json

def load_keywords(json_path):
    with open(json_path, 'r', encoding='utf-8') as file:
        keywords_data = json.load(file)
    return keywords_data

def process_text(input_text, keywords):
    for keyword_info in keywords:
        for keyword in keyword_info['NAMES']:
            pattern = re.compile(rf'\s*elainamod:{keyword}\s*', re.IGNORECASE)
            input_text = re.sub(pattern, f'{keyword}', input_text)
        for keyword in keyword_info['NAMES']:
            pattern = re.compile(rf'\s*{keyword}\s*', re.IGNORECASE)
            input_text = re.sub(pattern, f' elainamod:{keyword} ', input_text)

    return input_text

if __name__ == "__main__":
    if len(sys.argv) == 1:
        print("Usage: python format.py cards.json")
        sys.exit(1)

    keywords_json_file = "keywords.json"
    keywords_data = load_keywords(keywords_json_file)

    input_text_file = sys.argv[1]
    try:
        with open(input_text_file, 'r', encoding='utf-8') as file:
            input_text = file.read()
    except FileNotFoundError:
        print(f"Error: File not found - {input_text_file}")
        sys.exit(1)

    processed_text = process_text(input_text, keywords_data)

    try:
        with open(input_text_file, 'w', encoding='utf-8') as file:
            file.write(processed_text)
    except Exception as e:
        print(f"Error writing to file - {input_text_file}: {e}")
        sys.exit(1)
