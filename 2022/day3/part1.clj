(require '[clojure.set :as set]
         '[clojure.string :as s])

(defn parse-line [line]
  (let [half (/ (count line) 2)]
    [(subs line 0 half) (subs line half)]))

(defn item-in-both [[compartment-1 compartment-2]]
  (let [s1 (into #{} compartment-1)
        s2 (into #{} compartment-2)]
    (first (set/intersection s1 s2))))

(defn priority [c]
  (let [c-int (int c)]
    (cond
      (and (>= c-int 97) (<= c-int 122)) (- c-int 96)
      (and (>= c-int 65) (<= c-int 90))  (- c-int 38))))

(let [lines (s/split (slurp "2022/day3/input.txt") #"\n")]
  (->> lines
       (map parse-line)
       (map item-in-both)
       (map priority)
       (apply +)))

