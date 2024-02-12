     OVERVIEW 

This project provides a modeling environment aimed at extending an abstract model, 
defining the run method, which processes all calculations based on a provided data file. 
The environment includes a controller class using runtime assignment of values 
to model fields via reflection and a custom Bind annotation to access only fields that we desire.

    
    USAGE/MAKE SURE TO DO

this projects assumes that in the data file the first row is dedicated for years during which the variables/fields 
we want to use the model on had some specific values. The number of columns will then be stored in the LL field in the model class.
Make sure that the data file is a tsv file and to make new fields and to be displayed in the GUI view it is needed to annotate this
field with @BIND.
    
