import argparse
import os


def remove_duplicates(values):
    output = []
    seen = set()
    for value in values:
        if value not in seen:
            output.append(value)
            seen.add(value)
    return output


def text_to_list(text_file):
    with open(text_file) as f:
        word = f.readlines()
        return word


def write_array(import_list, write_name):
    writer = open(write_name, "a")
    for word in import_list:
        writer.write("<item>" + word.replace("\n", "").replace("\t", "").title().strip() + "</item>\n")
    writer.close()


def write(import_list, write_name):
    writer = open(write_name, "a")
    for word in import_list:
        writer.write("\t\t"+word.strip()+"\n")
    writer.close()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="Help import test files")
    group = parser.add_mutually_exclusive_group()
    group.add_argument('-i', '--importer', help="import .txt file")
    group.add_argument('-d', '--duplicate', help="removed duplicates from list")
    args = parser.parse_args()

    if args.importer:
        file = args.importer
        filename, file_extension = os.path.splitext(file)
        word = text_to_list(file)
        write_array(word, filename + "_import" + file_extension)
        print("Wrote " + str(word.__len__()) + " lines")

    if args.duplicate:
        file = args.duplicate
        word = text_to_list(file)
        filename, file_extension = os.path.splitext(file)
        rem_d = remove_duplicates(word)
        line_delta = word.__len__() - rem_d.__len__()
        write(word, filename + "_duplicate" + file_extension)
        print("Removed " + str(line_delta) + " lines")