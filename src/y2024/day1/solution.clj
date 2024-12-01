(ns y2024.day1.solution
  (:require [clojure.string :as s]))

(defn- transpose [m]
  (apply mapv vector m))

(defn- parse-int [s]
  (Integer/parseInt s))

(defn- parse-input [input]
  (let [lines (s/split-lines input)]
    (->> lines
         (map (fn [line] (s/split line #"   ")))
         (transpose)
         (map (fn [xs] (map parse-int xs))))))

(defn- distance [x1 x2] (abs (- x1 x2)))

;; Part 1
(let [[list1 list2] (parse-input (slurp "src/y2024/day1/input.txt"))
      list1-sorted (sort list1)
      list2-sorted (sort list2)]
  (apply + (map distance list1-sorted list2-sorted)))

(defn- similarity-inc [freqs x] (* x (get freqs x 0)))

;; Part 2
(let [[list1 list2] (parse-input (slurp "src/y2024/day1/input.txt"))
      freqs (frequencies list2)]
  (->> list1
       (map (partial similarity-inc freqs))
       (apply +)))
