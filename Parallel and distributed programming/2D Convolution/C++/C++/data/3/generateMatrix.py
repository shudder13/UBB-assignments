import random


m = 10
n = 10000
mKernel = 5
nKernel = 5

f = [[random.randint(0, 100) for _ in range(n)] for _ in range(m)]
kernel_value = round(random.random(), 2)
kernel = [[kernel_value for _ in range(nKernel)] for _ in range(mKernel)]

with open('f.txt', 'w') as file:
    file.write(f'{m} {n}\n')
    for row in f:
        for element in row:
            file.write(f'{element} ')
        file.write('\n')

with open('kernel.txt', 'w') as file:
    file.write(f'{mKernel} {nKernel}\n')
    for row in kernel:
        for element in row:
            file.write(f'{element} ')
        file.write('\n')
