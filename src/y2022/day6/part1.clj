(ns y2022.day6.part1)

(let [buffer (slurp "src/y2022/day6/input.txt")]
  (->> buffer
     (partition 4 1)
     (map-indexed vector)
     (drop-while (fn [[_ part]]
                   (not= (count (set part))
                         (count part))))
     (first)
     (first)
     ((partial + 4))))
