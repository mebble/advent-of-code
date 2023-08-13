(ns y2022.day7.part2
  (:require [y2022.day7.parse :refer [parse-line build-filesystem]]
            [clojure.string :as s]))

(def total-space 70000000)
(def unused-space-req 30000000)

(defn- add-dir-sizes [filesystem dir-sizes]
  (->> filesystem
       (map (fn [[item-name {:keys [type size] :as item-val}]]
              (if (= :file type)
                size
                (let [d-size (add-dir-sizes item-val dir-sizes)]
                  (swap! dir-sizes conj {:name item-name :size d-size})
                  d-size))))
       (apply +)))

(defn dir-sizes [filesystem]
  (let [sizes (atom [])]
    (add-dir-sizes filesystem sizes)
    @sizes))

(let [lines (s/split-lines (slurp "src/y2022/day7/input.txt"))
      dirs (->> lines
                (map parse-line)
                (build-filesystem)
                (dir-sizes))
      root-size (->> dirs
                     (filter (fn [{:keys [name]}] (= "/" name)))
                     (first)
                     (:size))
      unused-space (- total-space root-size)
      new-space-req (- unused-space-req unused-space)]
  (->> dirs
       (filter #(>= (:size %) new-space-req))
       (apply min-key :size)
       (:size)))
