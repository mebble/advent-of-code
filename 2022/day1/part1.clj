(require '[clojure.string :as s])

(let [ss (s/split (slurp "2022/day1/input.txt") #"\n\n")]
  (->> ss
       (map #(s/split % #"\n"))
       (map (fn [calories-str] (map #(Integer/parseInt %) calories-str)))
       (map (fn [calories-seq] (apply + calories-seq)))
       (apply max)))
