(require '[clojure.set :as set]
         '[clojure.string :as s])

(defn priority [c]
  (let [c-int (int c)]
    (cond
      (and (>= c-int 97) (<= c-int 122)) (- c-int 96)
      (and (>= c-int 65) (<= c-int 90))  (- c-int 38))))

(defn common-item-type [group]
  (->> group
       (map set)
       (reduce set/intersection)
       (first)))

(let [lines (s/split (slurp "2022/day3/input.txt") #"\n")
      groups (partition 3 lines)]
  (->> groups
       (map common-item-type)
       (map priority)
       (apply +)))
