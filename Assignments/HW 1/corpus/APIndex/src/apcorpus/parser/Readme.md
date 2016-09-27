#Search - Homework 1

## Task -1: GenerateIndex.java

GenerateIndex accepts a index path to write index, then processes XML document object and parses the file by extracting DOCNO,HEAD,BYLINE,DATELINE and TEXT tags. If there are multiple tags, each tag is traversed and then concatinated. Current solution offers a O(n) object generation (String Contenation). The Tag is added to the index only if it is present, i.e tag !="".

This same class is overloaded using multiple Analysers for Task-2.

Question 1: 
Number of Documents in the corpus:

Following are the statistics of the corpus using standard Analyser,

Total number of documents in the corpus:84474
Size of the vocabulary for this field:-1
Number of tokens for this field:24769551
Number of postings for this field:16806814
Size of the vocabulary for this field (Counted):219685

This number may vary because of following pre-processing steps,
1. Since SAX parser doesn't recognize &, a python program is used to pre-process files to replace & with &amp;
This program is available in the directory above current directory at [Folder](https://github.com/ganesh91/Information-Retrieval-Course/tree/master/Assignments/HW%201/corpus) named replace amp.py
2. Since SAX parser requires a fully formed XML, a <root> and </root> tag was added manually.

Question 2:
Different fields for different Data Types:
Lucene data types are sugar sub-classes used for determining type of indexing using most performant data structure. E.g for Standard Analyser, integer type field doesn't make sense. Also StringField can store only one token. It is assumed that only a single token is passed to this data structure. Whereas TextField data type support multiple lines. It can be tokenized, stemmed and processed for full document search capabilities, however string field cannot be tokenized further because it has a single token.

## Task -2 : IndexComparison.java

IndexComparison.java uses generateIndex.java and overloads its prepareIndexWriter function with multiple Analyzers.

| Tokenizer | Tokenization Applied | No.of Tokens | Stemming | Stop Words Removed | No.of Words in Dictionary |
|-----------|----------------------|--------------|----------|---------------------|--------------------------|
|KeywordAnalyzer| No, uses whole field as single token | 84474 | No | No | 83517 |
|SimpleAnalyzer| Yes | 34797587 | No | No | 163408 |
|StopAnalyzer| Yes | 24397068 | No | Yes | 163375 |
|StandardAnalyzer| Yes | 24769551 | Yes | Yes | 219685|

The unformatted output of the program is as follows,

```
----Standard Analyzer-------
Total number of documents in the corpus:84474
Size of the vocabulary for this field:-1
Number of tokens for this field:24769551
Number of postings for this field:16806814
Size of the vocabulary for this field (Counted):219685
----Keywords Analyzer-------
Total number of documents in the corpus:84474
Size of the vocabulary for this field:-1
Number of tokens for this field:84474
Number of postings for this field:84474
Size of the vocabulary for this field (Counted):83517
----Simple Analyzer-------
Total number of documents in the corpus:84474
Size of the vocabulary for this field:-1
Number of tokens for this field:34797587
Number of postings for this field:17761253
Size of the vocabulary for this field (Counted):163408
----Stop Analyzer-------
Total number of documents in the corpus:84474
Size of the vocabulary for this field:-1
Number of tokens for this field:24397068
Number of postings for this field:15963567
Size of the vocabulary for this field (Counted):163375
```

