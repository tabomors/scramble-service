(ns scramble-service.scramble)

(defn scramble? [str1 str2]
  (let [str1 (frequencies str1)
        str2 (frequencies str2)]
    (every? (fn [[char2 times2]]
              (when-let [times1 (get str1 char2)]
                (<= times2 times1))) str2)))

(comment
  (scramble? "rekqodlw" "world")
  (scramble? "cedewaraaossoqqyt" "codewars")
  (scramble? "katas"  "steak")
  )


