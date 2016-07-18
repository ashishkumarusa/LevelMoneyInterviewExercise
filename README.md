# LevelMoneyInterviewExercise
Install the appliaction on any android device with min OS verison 4.0.

Make sure internet connection is there else a Toast message would be displayed.

The first laucher activity simply contains a button and click of that would load all transactions for set of credentials as specified in LevelMoney docs.

On this activity there is one switch you can turn on/off if you want to ignore or include donuts related expenses in your spending.This toggling would upadte the result in text view. By default switch is off means ignore-donuts is false.

Important assumptions: 

1. Since both spent and income are long type hence computed of average spent and income are also long trucating any decimal value to whole integer.

2. I am utilizing android's listview to show each and every transaction in a seperate row rather than showing all transactions separated by comma(as asked to do in the intervew problem description).

3. Due to lack of time proper exceptions handling might not be there but core functionality is working as expected.


UI screenshots:

https://github.com/ashishkumarusa/LevelMoneyInterviewExercise/blob/master/device-2016-07-18-011631.png
https://github.com/ashishkumarusa/LevelMoneyInterviewExercise/blob/master/device-2016-07-18-011713.png
