
import re

replacements = {
    r"Malaysia": "Indonesia",
    r"JAS APIMS": "KLHK ISPU",
    r"Jabatan Alam Sekitar": "Kementerian Lingkungan Hidup dan Kehutanan (KLHK)",
    r"IPU": "ISPU",
    r"🇲🇾": "🇮🇩",
    r"Negara Bagian": "Provinsi",
    r"TM Unifi Plus Box": "IndiHome TV Box",
    r"unifi TV": "IndiHome TV",
    r"TM": "Operator",
    r"14 negara bagian": "38 provinsi",
}

def replace_text(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    for old, new in replacements.items():
        content = content.replace(old, new)

    # Special replacements for city lists if needed (I will do this manually or with more specific regex)
    # The previous edits already handled some.

    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)

replace_text("PANDUAN_PENGGUNA.md")
