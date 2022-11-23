> Backus Naur Form

**\<program>** ::= \<states> \<initial_state>
\<final_states> \<alphabet> \<transitions>  
**\<states>** ::= \<state> | \<states>  
**\<initial_state>** ::= \<state>  
**\<final_states>** ::=\<final_state> | \<final_states>  
**\<final_state>** ::= \<state>  
**\<alphabet>** ::= \<alpha> | \<alphabet>  
**\<transitions>** ::= \<transition> | \<transitions>  
**\<state>** ::= \<string>  
**\<alpha>** ::= \<char>  
**\<transition>** ::= \<state> \<alphas> \<state>  
**\<alphas>** ::= \<alpha> | \<alphas>  
**\<string>** ::= \<char> | \<char> \<string>  
**\<char>** ::= \<letter> | \<digit> | + | -  
**\<letter>** ::= a | b | ... | z | A | B | .. | Z   
**\<digit>** ::= 0 | 1 | ... | 9  
