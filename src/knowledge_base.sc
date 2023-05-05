theme: /
    # мини база знаний
   
    state: ProtPos || noContext = true
        q!: эй
        q!: * {прот* * посу*} *
        a: Инструкция по протирке посуды. Ссылка: https://coda.io/d/_dMcDuoSN4e7/_suSE4#_luAfx
        inlineButtons:
            {text:"Вернуться к Чек-листу", callback_data: "repit_step"}
    
    state: ProtPrib || noContext = true
        q!: * {прот* * прибор*} *
        a: Инструкция по протирке приборов. Ссылка: https://coda.io/d/_dMcDuoSN4e7/_suSE4#_luAfx    
        inlineButtons:
            {text:"Вернуться к Чек-листу", callback_data: "repit_step"}
        
    state: ProtPover || noContext = true
        q!: * {прот* * (повер*|стол*)} *
        a: Инструкция по протирке поверхностей. Ссылка: https://coda.io/d/_dMcDuoSN4e7/_suSE4#_luAfx   
        inlineButtons:
            {text:"Вернуться к Чек-листу", callback_data: "repit_step"}
        
    state: InstAll
        a: Инструкция по ... Ссылка: https://coda.io/d/_dMcDuoSN4e7/_suSE4#_luAfx     
        inlineButtons:
            {text:"Вернуться к Чек-листу", callback_data: "repit_step"}  
            
            
            