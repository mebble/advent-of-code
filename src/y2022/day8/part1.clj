(ns y2022.day8.part1
  (:require [clojure.string :as s]
            [y2022.day8.parse :refer [parse-trees]]
            [utils.core :refer [transpose]]))

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
