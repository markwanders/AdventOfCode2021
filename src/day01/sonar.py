with open("input.txt") as f:
    lines = [int(s) for s in f.readlines()]

print(f"Part 1: {len([x for i, x in enumerate(lines) if x > lines[i - 1]])}")
print(f"Part 2: {sum(a < b for a, b in zip(lines, lines[3:]))}")
