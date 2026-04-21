Project: weblog-analyzer (turned website access analyzer) 
Authors: David J. Barnes and Michael Kölling and Rachael Bielawa

This project *was* part of the material for chapter 4 of the book:

   Objects First with Java - A Practical Introduction using BlueJ
   Sixth edition
   David J. Barnes and Michael Kölling
   Pearson Education, 2016


Purpose of project: To transform an existing program and extend its abilities in order to log web access information potentially as a basis for webscraping functionality. 

The LogfileReader expects to read a file, weblog.txt,
containing lines of data in the format:

    year month day hour minute

month values are in the range 1-12 and day values in the range 1-31.
If the sample file cannot be found, the reader will create some simulated
data. Alternatively, use the LogfileCreator to create some random data.
Use its createFile method to give a file name and the number of entries
to create.
