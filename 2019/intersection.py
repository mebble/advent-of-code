from itertools import product

with open('wires.txt') as f:
    data = f.read()

wire1_str, wire2_str = data.split('\n')[:2]

def grow(point, dest):
    (x, y) = point
    direction = dest[0]
    dist = int(dest[1:])
    if direction == 'R':
        return (x + dist, y)
    if direction == 'L':
        return (x - dist, y)
    if direction == 'U':
        return (x, y + dist)
    if direction == 'D':
        return (x, y - dist)

def path_to_points(path):
    dests = path.split(',')
    points = [(0, 0)]
    for dest in dests:
        new_point = grow(points[-1], dest)
        points.append(new_point)

    return points

def points_to_lines(points):
    lines = []
    for i in range(1, len(points)):
        p1 = points[i-1]
        p2 = points[i]
        lines.append((p1, p2))
    return lines

def get_intersection(line1, line2):
    '''
    line1 and line2 are horizontal and vertical only
    '''
    line1_o = orientation(line1)
    line2_o = orientation(line2)
    if line1_o == line2_o:
        return None
    if line1_o == 'VERTICAL' and line2_o == 'HORIZONTAL':
        vertical_line = line1
        horizontal_line = line2
    if line1_o == 'HORIZONTAL' and line2_o == 'VERTICAL':
        vertical_line = line2
        horizontal_line = line1

    ((vx1, _), _) = vertical_line
    ((hx1, hy1), (hx2, _)) = horizontal_line
    if (hx1 <= vx1 <= hx2) or (hx2 <= vx1 <= hx1):
        return (vx1, hy1)
    return None

def orientation(line):
    (p1, p2) = line
    (x1, y1) = p1
    (x2, y2) = p2
    if (x1 == x2):
        return 'VERTICAL'
    if (y1 == y2):
        return 'HORIZONTAL'
    return 'OBLIQUE'

def man_dist(point1, point2):
    (x1, y1) = point1
    (x2, y2) = point2
    return abs(x1 - x2) + abs(y1 - y2)

if __name__ == '__main__':
    wire1_pts = path_to_points(wire1_str)
    wire2_pts = path_to_points(wire2_str)
    wire1_lines = points_to_lines(wire1_pts)
    wire2_lines = points_to_lines(wire2_pts)

    intersections = []
    for (line1, line2) in product(wire1_lines, wire2_lines):
        intersection = get_intersection(line1, line2)
        if intersection:
           intersections.append(intersection)

    # print(intersections)

    distances = [man_dist(x, (0, 0)) for x in intersections]
    min_dist = min(distances)

    print(f'Smallest distance: {min_dist}')
