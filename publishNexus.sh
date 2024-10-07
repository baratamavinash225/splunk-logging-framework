echo "====== Publish 2.13 Version Scala ======"
sbt "++ 2.13.4" clean compile copyResources assembly publish
echo "====== Publish 2.12 Version Scala ======"
sbt "++ 2.12.13" clean compile copyResources assembly publish