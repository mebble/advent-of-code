with open('opcodes.txt') as f:
    data = f.read()

opcodes = [int(x) for x in data.split(',')]

# setting the state to the "1202 program alarm" state
opcodes[1] = 12
opcodes[2] = 2

class Intcode:
    def __init__(self, memory):
        self.memory = memory[:]
        self.pointer = 0

    def move(self):
       self.pointer += 4

    def run(self):
        opcode = self.memory[self.pointer]
        while opcode != 99:
            arg1_address = self.memory[self.pointer + 1]
            arg2_address = self.memory[self.pointer + 2]
            res_address = self.memory[self.pointer + 3]

            if opcode == 1:
                self.memory[res_address] = self.memory[arg1_address] + self.memory[arg2_address]
            elif opcode == 2:
                self.memory[res_address] = self.memory[arg1_address] * self.memory[arg2_address]

            self.move()
            opcode = self.memory[self.pointer]

    def __str__(self):
        return str(self.memory)

def day_2_part_2():
  for noun in range(101):
      for verb in range(101):
          oc = opcodes[:]
          oc[1] = noun
          oc[2] = verb
          program = Intcode(oc)
          program.run()
          output = program.memory[0]
          if output == 19690720:
              return (noun, verb)

print(day_2_part_2())
