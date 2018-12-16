(defproject scramble-service "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [thheller/shadow-cljs "2.7.6"]]
  :source-paths ["src/clj" "src/cljc"]
  :aliases {"dev" ["with-profile" "+cljs" "run" "-m" "shadow.cljs.devtools.cli" "watch" "cljs-repl" "app"]}
  :test-paths ["test/clj" "test/cljc"]
  ; :main ^:skip-aot scramble-service.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :cljs {:source-paths ["src/cljs" "src/cljc"]
                    :dependencies [[reagent "0.8.1"]
                                   [cljs-ajax "0.7.5"]]}})
