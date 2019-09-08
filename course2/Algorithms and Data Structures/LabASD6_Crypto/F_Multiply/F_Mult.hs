main = do
    firstStr <- getLine
    secondStr <- getLine
    print (read firstStr * read secondStr)
