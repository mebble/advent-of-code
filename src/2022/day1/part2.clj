(require '[clojure.string :as s]
         '[utils.core :refer [vec-remove]])

(defn max-three
  [maxes x]
  (if (< (count maxes) 3)
    (conj maxes x)
    (let [candidates (conj maxes x)
          candidates-enumerated (map-indexed vector candidates)
          [min-i, _] (apply min-key second candidates-enumerated)]
      (vec-remove min-i candidates))))

(let [ss (s/split (slurp "src/2022/day1/input.txt") #"\n\n")]
  (->> ss
       (map #(s/split % #"\n"))
       (map (fn [calories-str] (map #(Integer/parseInt %) calories-str)))
       (map (fn [calories-seq] (apply + calories-seq)))
       #_(#(doto % prn))
       (reduce max-three [])
       (apply +)))
