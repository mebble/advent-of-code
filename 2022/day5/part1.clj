(require '[clojure.string :as s])

(defn- flip [f]
  ;; #util https://stackoverflow.com/a/44995482/5811761
  (fn [& xs] (apply f (reverse xs))))

(def split* 
  ;; #util
  (flip s/split))

(defn transpose [m]
  ;; #util https://stackoverflow.com/a/10347404/5811761
  (apply mapv vector m))

(defn parse-stacks [stacks-str]
  (->> stacks-str
       (split* #"\n")
       (transpose)
       (filter (partial some #(Character/isLetterOrDigit %)))
       (map (partial filter (partial not= \space)))
       (map (fn [col] (vector (Integer/parseInt (str (last col)))
                              (drop-last col))))
       (into (sorted-map))))

(defn parse-procedure [procedure-str]
  (->> procedure-str
       (split* #"\n")
       (map (partial re-seq #"\d+"))
       (map (fn [digit-seq] (map #(Integer/parseInt %) digit-seq)))
       (map (fn [[n from to]] {:n n :from from :to to}))))

(defn do-move [stacks move]
  (let [{:keys [from to n]} move
        src (stacks from)
        dst (stacks to)
        [to-move remaining] (split-at n src)]
    (-> stacks
        (assoc from remaining)
        (assoc to (concat (reverse to-move) dst)))))

(defn move-crates [stacks moves]
  (reduce do-move stacks moves))

(let [[stacks-str procedure-str] (s/split (slurp "2022/day5/input.txt") #"\n\n")
      stacks (parse-stacks stacks-str)
      moves (parse-procedure procedure-str)]
  (->> (move-crates stacks moves)
       (vals)
       (map first)
       (s/join)))
