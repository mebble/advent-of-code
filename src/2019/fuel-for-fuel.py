
with open('module-weights.txt') as f:
    data = f.read()

masses = [int (x) for x in data.split()]

def recursive_fuel_of_module(mass, total_fuel):
    fuel = (mass // 3) - 2
    if fuel <= 0:
        return total_fuel
    return recursive_fuel_of_module(fuel, total_fuel + fuel)

total_fuel_req = sum([recursive_fuel_of_module(x, 0) for x in masses])
print(f'Total fuel required: {total_fuel_req}')
