file = fopen('map.txt', 'r');

n = fscanf(file, '%d%d%d', 3);
n
x = zeros(1, n(3));
y = zeros(1, n(3));
for i = 1:n(3)
    tmp = fscanf(file, '%f%f', 2);
    x(i) = tmp(1);
    y(i) = tmp(2);
    tmp;
end

plot(x, y, 'x');