(ns y2024.day4.solution
  (:require [clojure.string :as s]))

(def left [[0 0] [0 -1] [0 -2] [0 -3]])
(def right [[0 0] [0 1] [0 2] [0 3]])
(def top [[0 0] [-1 0] [-2 0] [-3 0]])
(def bottom [[0 0] [1 0] [2 0] [3 0]])
(def top-left [[0 0] [-1 -1] [-2 -2] [-3 -3]])
(def top-right [[0 0] [-1 1] [-2 2] [-3 3]])
(def bottom-left [[0 0] [1 -1] [2 -2] [3 -3]])
(def bottom-right [[0 0] [1 1] [2 2] [3 3]])
(def dirs [left right top bottom top-left top-right bottom-left bottom-right])

(defn- get-letters [grid loc dir]
  (let [[y x] loc]
    (->> dir
         (map (fn [[dy dx]] [(+ y dy) (+ x dx)]))
         (map (fn [loc] (get-in grid loc))))))

(defn- is-xmas [grid loc dir]
  (->> (get-letters grid loc dir)
       (s/join)
       (#(= "XMAS" %1))))

(defn- num-xmas [grid loc]
  (->> dirs
       (map (partial is-xmas grid loc))
       (filter identity)
       (count)))

;; Part 1
(let [word-grid (s/split-lines (slurp "src/y2024/day4/input.txt"))
      ys (range (count word-grid))
      xs (range (count (first word-grid)))]
  (apply + (for [y ys
                 x xs]
             (num-xmas word-grid [y x]))))

(def slope-up [[1 -1] [0 0] [-1 1]])
(def slope-down [[-1 -1] [0 0] [1 1]])
(def mas #{\M \A \S})

(defn- is-x-mas [grid loc]
  (and (= \A (get-in grid loc))
       (= mas (set (get-letters grid loc slope-up)))
       (= mas (set (get-letters grid loc slope-down)))))

;; Part 2
(let [word-grid (s/split-lines (slurp "src/y2024/day4/input.txt"))
      ys (range (count word-grid))
      xs (range (count (first word-grid)))]
  (->> (for [y ys
             x xs]
         (is-x-mas word-grid [y x]))
       (filter identity)
       (count)))
