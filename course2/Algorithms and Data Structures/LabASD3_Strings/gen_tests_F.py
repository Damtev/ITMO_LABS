from random import randint


MIN_STR_LEN = 10
MAX_STR_LEN = 40
RANDOM_TESTS_COUNT = 2


def gen_testfile():
    with open("F.in", "w") as file:
        gen_random_tests(file, RANDOM_TESTS_COUNT)

def gen_random_tests(file, count: int):
    file.write(str(count) + "\n")
    for i in range(count):
        length_first = randint(MIN_STR_LEN, MAX_STR_LEN)
        s1 = _get_random_string(length_first)
        file.write(s1 + "\n")

def _get_random_string(length):
    return "".join(chr(randint(ord('a'), ord('c'))) for _ in range(length))


if __name__ == "__main__":
    gen_testfile()
