# Marks

Store marks and information for a course (e.g.: University). A `Course` contains many `Year`s which each contain many `Module`s which each contain many `Assessment`s.

Run `gradle --console plain run -q` to be asked for course information on the command line and then a filename. It will create an excel file with that name containing all the course information provided and formulas linking together. It includes spaces to add marks for each assessment and the formulas will calculate the average marks and grades for each module, year and overall. I am adding new features such as calculating the average mark needed in remaining assessments to achieve a target grade for each module.