package scala.com.demo

import org.apache.log4j.Level
import org.apache.log4j.Logger.getLogger
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.Map
import scala.com.demo.model.MyALS

import org.junit.{Before, Test, After}

class MyALS_test {

  var train: RDD[Rating] = _
  var test: RDD[Rating] = _
  var mapper: Map[Int, String] = _
  var sc: SparkContext = _

  def _rddStr2rddRatings(rddStr: RDD[String]): RDD[Rating] =
	rddStr.map(_.split(',') match { case Array(user, item, rate, ts) =>
	  Rating(user.toInt, item.toInt, rate.toDouble)
	})

  def _id2Name(mapper: RDD[String]): Map[Int, String] =
	mapper.flatMap {
	  line =>
		val items = line.split(',')
		if (items(0).isEmpty) None
		else Some((items(0).toInt, items(1)))
	}.collectAsMap()

  @Before
  def prepareData(): Unit = {
	getLogger("org.apache.spark").setLevel(Level.WARN)
	getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

	sc = new SparkContext(new SparkConf().setAppName("ALS").setMaster("local[*]"))

	// read data from HDFS and delete header line
	val rawRatings = sc.textFile("hdfs://localhost:9000/input/ml_data/ratings.csv")
	  .zipWithIndex().filter(_._2 >= 1).keys

	val rawMovies = sc.textFile("hdfs://localhost:9000/input/ml_data/movies.csv")
	  .zipWithIndex().filter(_._2 >= 1).keys

	// split raw dataset into training set and testing set and transform to RDD[Rating]
	val randomSeq = rawRatings.randomSplit(Array(0.7, 0.3))
	train = _rddStr2rddRatings(randomSeq(0))
	//	train = _rddStr2rddRatings(sc.parallelize(rawRatings.randomSplit(Array(1,0))(0).collect(), numSlices = 100))
	test = _rddStr2rddRatings(randomSeq(1))

	mapper = _id2Name(rawMovies)

	println("Preparation completed!")
  }

  @After
  def afterProcessing(): Unit =
	sc.stop()

  @Test
  def test01(): Unit = {
	val myALS = new MyALS(30, 20, 0.1)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test02(): Unit = {
	val myALS = new MyALS(50, 20, 0.1)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test03(): Unit = {
	val myALS = new MyALS(30, 20, 0.01)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test04(): Unit = {
	val myALS = new MyALS(50, 20, 0.01)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test05(): Unit = {
	val myALS = new MyALS(50, 20, 1)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test06(): Unit = {
	val myALS = new MyALS(30, 20, 1)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test07(): Unit = {
	val myALS = new MyALS(50, 20, 0.001)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test08(): Unit = {
	val myALS = new MyALS(30, 20, 0.001)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(80)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test09(): Unit = {
	val myALS = new MyALS(50, 20, 0.01)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(120)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test10(): Unit = {
	val myALS = new MyALS(30, 20, 0.01)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(120)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test11(): Unit = {
	val myALS = new MyALS(50, 20, 0.01)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(50)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

  @Test
  def test12(): Unit = {
	val myALS = new MyALS(30, 20, 0.01)
	myALS.fit(train)
	//	myALS.autoFit(train)

	val prediction = myALS.predict(50)

	val score = myALS.score(sc, train, test, prediction, mapper.keys.toArray)

	score.foreach(
	  t =>
		printf("%s: %.4f\n", t._1, t._2)
	)

  }

}
