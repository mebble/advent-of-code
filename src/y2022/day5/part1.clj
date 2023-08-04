(require '[clojure.string :as s]
         '[y2022.day5.parse :refer [parse-stacks parse-procedure]])

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

(let [[stacks-str procedure-str] (s/split (slurp "src/y2022/day5/input.txt") #"\n\n")
      stacks (parse-stacks stacks-str)
      moves (parse-procedure procedure-str)]
  (->> (move-crates stacks moves)
       (vals)
       (map first)
       (s/join)))
