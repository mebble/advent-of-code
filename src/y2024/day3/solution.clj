(ns y2024.day3.solution
  (:require [y2024.common :refer [parse-int]]))

(defn- parse-muls [input]
  (re-seq #"mul\(\d+,\d+\)" input))

(defn- parse-mul [mul]
  (when-let [matches (re-matches #"mul\((\d+),(\d+)\)" mul)]
    (let [[_ x1 x2] matches]
      [(parse-int x1)
       (parse-int x2)])))

(defn- multiply [[x1 x2]]
  (* x1 x2))

;; Part 1
(let [muls (parse-muls (slurp "src/y2024/day3/input.txt"))]
  (->> muls
       (map parse-mul)
       (filter (complement nil?))
       (map multiply)
       (apply +)))

