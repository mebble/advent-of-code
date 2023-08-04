(require '[clojure.string :as s])

(def merge-ranges (juxt #(apply min %) #(apply max %)))

(defn parse-line [line]
  (let [pairs-strs (s/split line #",")]
    (->> pairs-strs
         (map (fn [pair-str] (s/split pair-str #"-")))
         (map (fn [bounds-str] (map #(Integer/parseInt %) bounds-str))))))

(defn- within? [[lower upper] n]
  (and (>= n lower)
       (<= n upper)))

(defn superset?
  "true if either range1 or range2 is a super of the other"
  [[range1 range2]]
  (let [extremes (merge-ranges (concat range1 range2))]
    (or (= extremes range1)
        (= extremes range2))))

(defn overlap?
  [[range1 range2]]
  (let [[from to] range1]
    (or (superset? [range1 range2])
        (within? range2 from)
        (within? range2 to))))

(let [lines (s/split (slurp "src/y2022/day4/input.txt") #"\n")]
  (->> lines
       (map parse-line)
       (filter overlap?)
       (count)))
