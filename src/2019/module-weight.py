
data = None

with open('module-weights.txt') as f:
    data = f.read()

masses = [int(x) for x in data.split()]

def calc_fuel_req(mass):
    return (mass // 3) - 2

total_fuel_req = sum([calc_fuel_req(x) for x in masses])
print(f'Total fuel required: {total_fuel_req}')
