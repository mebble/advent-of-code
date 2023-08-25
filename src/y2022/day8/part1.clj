(ns y2022.day8.part1
  (:require [clojure.string :as s]
            [utils.core :refer [transpose char->int]]))

(defn parse-trees [lines]
  (->> lines
       (map-indexed vector)
       (map (fn [[y row-str]] (map-indexed #(vector %1 y (char->int %2)) row-str)))
       (map (fn [row] (map (fn [[x y h]] { :x x :y y :h h }) row)))))

(defn tallest-trees [tree-grid]
  (->> tree-grid
       (map (fn [row]
              (->> row
                   (reductions (partial max-key :h))
                   (partition-by :h)
                   (map first))))
       (apply concat)))

(defn tallest-trees-count [tree-grid]
  (let [trees-from-left tree-grid
        trees-from-top (transpose trees-from-left)
        trees-from-right (map reverse trees-from-left)
        trees-from-bottom (map reverse trees-from-top)]
    (distinct (concat (tallest-trees trees-from-left)
                      (tallest-trees trees-from-right)
                      (tallest-trees trees-from-top)
                      (tallest-trees trees-from-bottom)))))

(let [lines (s/split-lines (slurp "src/y2022/day8/input.txt"))]
  (->> lines
       (parse-trees)
       (tallest-trees-count)
       (count)))
