from problems import *


def test_problem1():
    assert(problem1('Ana are mere rosii si galbene') == 'si')
    assert(problem1('') == '')


def test_problem2():
    assert((problem2(1, 5, 4, 1) - 5) < 0.0001)
    assert((problem2(1, 5, 1, 5) - 0) < 0.0001)
    assert((problem2(0, 0, 0, 0) - 0) < 0.0001)
    assert((problem2(1, 1, 7, 9) - 10) < 0.0001)


def test_problem4():
    assert(problem4('ana are ana are mere rosii ana') == ['mere', 'rosii'])
    assert(problem4('') == [''])
    assert(problem4('George Alex Alex Alexandru') == ['George', 'Alexandru'])


def test_problem5():
    assert(problem5([1, 2, 3, 4, 2]) == 2)
    assert(problem5([1, 2, 3, 4, 3]) == 3)
    assert(problem5([1, 2, 3, 4, 5, 1]) == 1)
    try:
        problem5([1, 2, 3, 4, 5, 6])
        raise Exception('Exceptie neprinsa la rularea testelor pentru problem 5.')
    except Exception:
        pass


def test_problem6():
    assert(problem6([2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]) == 2)
    try:
        problem6([1, 3, 5, 6, 8, 3, 3])
        raise Exception('Exceptie neprinsa la rularea testelor pentru problem 6.')
    except Exception:
        pass


def test_problem7():
    assert(problem7([7, 4, 6, 3, 9, 1], 2) == 7)
    assert(problem7([7, 4, 6, 3, 9, 1], 3) == 6)
    assert(problem7([7, 4, 6, 3, 9, 9, 1], 2) == 9)
    assert(problem7([7, 7, 4, 6, 3, 9, 9, 1], 2) == 9)
    assert(problem7([7, 7, 4, 6, 3, 9, 9, 1], 3) == 7)
    try:
        problem7([[7, 7, 4, 6, 3, 9, 9, 1]], 7)
        raise Exception('Exceptie neprinsa la rularea testelor pentru problem 7.')
    except Exception:
        pass


def test_problem8():
    assert(problem8(0) == [])
    assert(problem8(4) == [1, 10, 11, 100])
    assert(problem8(5) == [1, 10, 11, 100, 101])
    assert(problem8(7) == [1, 10, 11, 100, 101, 110, 111])
    assert(problem8(8) == [1, 10, 11, 100, 101, 110, 111, 1000])


def test_problem9():
    assert(problem9([
        [0, 2, 5, 4, 1],
        [4, 8, 2, 3, 7],
        [6, 3, 4, 6, 2],
        [7, 3, 1, 8, 3],
        [1, 5, 7, 9, 4]
    ], [((1, 1), (3, 3)), ((2, 2), (4, 4))]) == [38, 44])


def test_problem9_v2():
    assert (problem9_v2([
        [0, 2, 5, 4, 1],
        [4, 8, 2, 3, 7],
        [6, 3, 4, 6, 2],
        [7, 3, 1, 8, 3],
        [1, 5, 7, 9, 4]
    ], [((1, 1), (3, 3)), ((2, 2), (4, 4))]) == [38, 44])


def test_problem10():
    assert(problem10([
        [0, 0, 0, 1, 1],
        [0, 1, 1, 1, 1],
        [0, 0, 1, 1, 1]
    ]) == 2)


def test_problem10_v2():
    assert(problem10_v2([
        [0, 0, 0, 1, 1],
        [0, 1, 1, 1, 1],
        [0, 0, 1, 1, 1]
    ]) == 2)


def run_tests():
    test_problem1()
    test_problem2()
    test_problem4()
    test_problem5()
    test_problem6()
    test_problem7()
    test_problem8()
    test_problem9()
    test_problem9_v2()
    test_problem10()
    test_problem10_v2()
