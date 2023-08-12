(ns y2022.day7.part1
  (:require [y2022.day7.parse :refer [parse-line build-filesystem]]
            [clojure.string :as s]))

(defn- eligible? [n] (<= n 100000))

(defn- add-eligible-dirs [filesystem eligibles]
  (->> filesystem
       (map (fn [[item-name {:keys [type size] :as item-val}]]
              (if (= :file type)
                size
                (let [d-size (add-eligible-dirs item-val eligibles)]
                  (when (eligible? d-size) (swap! eligibles conj {:name item-name :size d-size}))
                  d-size))))
       (apply +)))

(defn eligible-dirs [filesystem]
  (let [eligibles (atom [])]
    (add-eligible-dirs filesystem eligibles)
    @eligibles))

(let [lines (s/split-lines (slurp "src/y2022/day7/input.txt"))]
  (->> lines
       (map parse-line)
       (build-filesystem)
       (eligible-dirs)
       (map :size)
       (apply +)))
