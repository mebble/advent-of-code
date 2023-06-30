;; part 1
(let [ss (clojure.string/split (slurp "2022/day1/input.txt") #"\n\n")]
  (->> ss
       (map #(clojure.string/split % #"\n"))
       (map (fn [calories-str] (map #(Integer/parseInt %) calories-str)))
       (map (fn [calories-seq] (apply + calories-seq)))
       (apply max)))

;; part 2
(defn vec-remove
  "remove elem in coll https://stackoverflow.com/a/18319708/5811761"
  [pos coll]
  (into (subvec coll 0 pos) (subvec coll (inc pos))))

(defn max-three
  [maxes x]
  (if (< (count maxes) 3)
    (conj maxes x)
    (let [candidates (conj maxes x)
          candidates-enumerated (map-indexed vector candidates)
          [min-i, _] (apply min-key second candidates-enumerated)]
      (vec-remove min-i candidates))))

(let [ss (clojure.string/split (slurp "2022/day1/input.txt") #"\n\n")]
  (->> ss
       (map #(clojure.string/split % #"\n"))
       (map (fn [calories-str] (map #(Integer/parseInt %) calories-str)))
       (map (fn [calories-seq] (apply + calories-seq)))
       #_(#(doto % prn))
       (reduce max-three [])
       (apply +)))
