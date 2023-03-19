'''
def problem1_old(text):
    """
    Sa se determine ultimul (din punct de vedere alfabetic) cuvant care poate aparea intr-un text
    care contine mai multe cuvinte separate prin spatiu.
    :param text: textul dat in problema
    :return: cuvantul cerut
    """
    cuvinte = text.lower.split(' ')
    cuvinte.sort()
    return cuvinte[-1]
'''

def problem1(text):
    """
    Sa se determine ultimul (din punct de vedere alfabetic) cuvant care paote aparea intr-un text
    care contine mai multe cuvinte separate prin spatiu.
    :param text: textul dat in problema
    :return: cuvantul cerut
    """
    cuvant_curent = ''
    ultimul_cuvant = ''
    for i in range(len(text) - 1, -1, -1):
        if text[i] == ' ':
            if cuvant_curent.lower() > ultimul_cuvant.lower():
                ultimul_cuvant = cuvant_curent
            cuvant_curent = ''
        else:
            cuvant_curent = text[i] + cuvant_curent
    if cuvant_curent > ultimul_cuvant:
        ultimul_cuvant = cuvant_curent
    return ultimul_cuvant


def problem2(x1, y1, x2, y2):
    """
    Sa se determine distanta euclidiana intre doua locatii identificate prin perechi de numere.
    :param x1: abscisa primului punct
    :param y1: ordonata primului punct
    :param x2: abscisa celui de-al doilea punct
    :param y2: ordonata celui de-al doilea punct
    :return: distanta euclidiana dintre cele doua puncte (float)
    """
    import math
    return math.sqrt((y2 - y1) ** 2 + (x2 - x1) ** 2)


def problem3(v1, v2):
    """
    Să se determine produsul scalar a doi vectori rari care conțin numere reale.
    Un vector este rar atunci când conține multe elemente nule. Vectorii pot avea oricâte dimensiuni.
    :param v1: primul vector
    :param v2: al doilea vector
    :return: produsul scalar al celor doi vectori
    """
    sum = 0
    for i in range(len(v1)):
        if v1[i] != 0 and v2[i] != 0:
            sum += v1[i] * v2[i]
    return sum


def problem4(text):
    """
    Sa se determine cuvintele care apar exact o singura data in acel text.
    :param text: textul dat
    :return: o lista continand cuvintele care apar o singura data in text
    """
    cuvinte = text.split(' ')
    aparitii_cuvinte = {}
    for cuvant in cuvinte:
        if cuvant in aparitii_cuvinte.keys():
            aparitii_cuvinte[cuvant] += 1
        else:
            aparitii_cuvinte[cuvant] = 1
    cuvinte = []
    for cuvant in aparitii_cuvinte.keys():
        if aparitii_cuvinte[cuvant] == 1:
            cuvinte.append(cuvant)
    return cuvinte


def problem5(array):
    """
    Pentru un sir cu n elemente care contin valori din multimea {1, 2, ..., n - 1} astfel incat o singura valoare
    se repeta de doua ori, sa se identifice acea valoare care se repeta.
    :param array: sirul cu n elemente din multimea {1, 2, ..., n - 1} in care o singura valoare se repeta de doua ori
    :return: valoarea care se repeta de 2 ori
    """
    sum = 0
    for element in array:
        sum += element
    n = len(array)
    expected_sum = n * (n - 1) / 2
    return sum - expected_sum


def problem6(array):
    """
    Pentru un sir cu n numere intregi care contine si duplicate, sa se determine elementul majoritar (care apare
    de mai mult de n / 2 ori).
    :param array: sirul de numere intregi
    :return: exceptie daca nu exista un element majoritar, care sa apara in sir de mai mult de n / 2 ori
            elementul majoritar, care apare de mai mult de n / 2 ori
    """
    # Boyer-Moore majority vote algorithm
    candidate = None
    count = 0
    for element in array:
        if count == 0:
            candidate = element
        elif element == candidate:
            count += 1
        else:
            count -= 1
    # check
    array_size = len(array)
    count = 0
    for element in array:
        if element == candidate:
            count += 1
            if count > array_size / 2:
                return candidate


def problem7(array, k):
    """
    Sa se determine al k-lea cel mai mare element al unui sir de numere cu n elemente (k < n).
    :param array: sirul de numere
    :param k: k din enunt
    :return: exceptie, daca k > n
            al k-lea cel mai mare element al sirului de numere
    """
    # quick selection algorithm
    if len(array) == 1:
        return array[0]

    pivot = array[0]
    smaller = [x for x in array if x < pivot]
    higher_or_equal = [x for x in array[1:] if x >= pivot]

    if len(higher_or_equal) == k - 1:
        return pivot
    elif len(higher_or_equal) > k - 1:
        return problem7(higher_or_equal, k)
    else:
        return problem7(smaller, k - len(higher_or_equal) - 1)


def problem8(n):
    """
    Sa se genereze toate numerele (in reprezentare binara) cuprinse intre 1 si n.
    :param n: numarul din enunt
    :return: lista continand toate acele numere in reprezentare binara
    """
    import queue
    result = []
    coada = queue.Queue()
    coada.put('1')
    while n:
        n -= 1
        element = coada.get()
        result.append(int(element))
        coada.put(element + '0')
        coada.put(element + '1')
    return result


def problem9(matrix, perechi):
    """
    Considerandu-se o matrice cu n x m elemente intregi si o lista cu perechi
    formate din coordonatele a 2 casute din matrice ((p,q) și (r,s)),
    sa se calculeze suma elementelor din sub-matricile identificate de fiecare pereche.
    """
    result = []
    for pereche in perechi:
        suma_submatrice = 0
        for i in range(pereche[0][0], pereche[1][0] + 1):
            for j in range(pereche[0][1], pereche[1][1] + 1):
                suma_submatrice += matrix[i][j]
        result.append(suma_submatrice)
    return result


def problem9_v2(matrix, perechi):
    """
    Considerandu-se o matrice cu n x m elemente intregi si o lista cu perechi
    formate din coordonatele a 2 casute din matrice ((p,q) și (r,s)),
    sa se calculeze suma elementelor din sub-matricile identificate de fiecare pereche.
    """
    result = [0] * len(perechi)
    # parcurg o singura data matricea si verific la fiecare element daca se afla in vreuna dintre submatricile mele
    for i, row in enumerate(matrix):
        for j, element in enumerate(row):
            for index_pereche, pereche in enumerate(perechi):
                if pereche[0][0] <= i and i <= pereche[1][0] and pereche[0][1] <= j and j <= pereche[1][1]:
                    result[index_pereche] += matrix[i][j]
    return result

def problem10(matrix):
    """
    Considerandu-se o matrice cu n x m elemente binare (0 sau 1) sortate crescator pe linii, sa se identifice indexul
    liniei care contine cele mai multe elemente de 1.
    :param matrix: matricea din enunt
    :return: indexul liniei care contine cele mai multe elemente de 1
    """
    max = -1
    max_row_index = -1
    for index, row in enumerate(matrix):
        count = 0
        for i in range(len(row) - 1, -1, -1):
            if row[i] == 1:
                count += 1
            else:
                break
        if count > max:
            max = count
            max_row_index = index
    return max_row_index + 1


def problem10_v2(matrix):
    """
    Considerandu-se o matrice cu n x m elemente binare (0 sau 1) sortate crescator pe linii, sa se identifice indexul
    liniei care contine cele mai multe elemente de 1.
    :param matrix: matricea din enunt
    :return: indexul liniei care contine cele mai multe elemente de 1
    """
    vector_linii = [1] * len(matrix)  # vector boolean, pe pozitia i e 1 cat timp nu s-a gasit un 0 pe linia respectiva
    n = len(matrix)
    m = len(matrix[0])
    for j in range(m - 1, -1, -1):  # parcurg pe coloane, de la dreapta la stanga
        for i in range(n):
            if matrix[i][j] == 0:
                vector_linii[i] = 0
                if vector_linii.count(1) == 1:  # daca e un singur 1 in vector
                    return vector_linii.index(1) + 1
