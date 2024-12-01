(ns y2022.day8.parse
  (:require [utils.core :refer [char->int]]))

(defn parse-trees [lines]
  (->> lines
       (map-indexed vector)
       (map (fn [[y row-str]] (map-indexed #(vector %1 y (char->int %2)) row-str)))
       (map (fn [row] (map (fn [[x y h]] { :x x :y y :h h }) row)))))
