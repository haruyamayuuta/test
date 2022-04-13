    
select T2.name
from test.Users as T1
inner join test.pets as T2
on T1.id=T2.user_id and T2.user_id=2;