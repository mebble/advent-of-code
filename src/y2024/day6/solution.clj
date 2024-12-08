(ns y2024.day6.solution
  (:require [clojure.string :as s]
            [y2024.common :refer [replace-at]]))

(def next-dirs (cycle [:right :down :left :up]))

(defn- front [guard]
  (let [dir (:dir guard)
        [y x] (:pos guard)]
    (case dir
      :up [(dec y) x]
      :right [y (inc x)]
      :down [(inc y) x]
      [y (dec x)])))

(defn- parse-input [input]
  (let [grid (vec (map (comp vec seq) (s/split-lines input)))
        width (count (first grid))
        offset (s/index-of (s/replace input #"\n" "") "^")
        y (quot offset width)
        offset-y (* y width)
        x (- offset offset-y)]
    [grid
     {:pos [y x] :dir :up}]))

(defn- walk [grid guard]
  (loop [grid grid
         guard guard
         positions 1
         dirs next-dirs]
    (let [[y x] (:pos guard)
          next-pos (front guard)
          object (get-in grid next-pos)]
      (cond
        (nil? object) positions
        (= \# object) (recur grid
                             (assoc guard :dir (first dirs))
                             positions
                             (rest dirs))
        (= \X object) (recur (update grid y replace-at x \X)
                             (assoc guard :pos next-pos)
                             positions
                             dirs)
        :else         (recur (update grid y replace-at x \X)
                             (assoc guard :pos next-pos)
                             (inc positions)
                             dirs)))))

;; Part 1
(let [[grid guard] (parse-input (slurp "src/y2024/day6/input.txt"))]
  (walk grid guard))
