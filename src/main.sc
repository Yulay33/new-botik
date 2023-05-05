require: requirements.sc

theme: /

    # стартовый стейт, с него начинается общение с пользователем, если через ссылку не было передано
    # дополнительных параметров. 
    # например: https://t.me/bot_name?start=cleaning-checklist
    state: Start
        # тег "q!" - (от англ. "question" — вопрос ) тег для паттернов
        # Это глобальный тег: переход по нему возможен из любого другого стейта в сценарии.
        # $regex - паттерн для обработки регулярных выражений. Подробнее тут: https://help.just-ai.com/docs/ru/Patterns/advanced_patterns#regexpregexp_i
        q!: $regex</start>
        a: Начнём, а?.
    
    state: Help || noContext = true
        # тег "q!" - (от англ. "question" — вопрос ) тег для паттернов
        # Это глобальный тег: переход по нему возможен из любого другого стейта в сценарии.
        # $regex - паттерн для обработки регулярных выражений. Подробнее тут: https://help.just-ai.com/docs/ru/Patterns/advanced_patterns#regexpregexp_i
        q!: * (help/помог*/помощ*) *
        a: Я Бот Русич, твой виртуальный помощник
        a: Я выполняю следующие команды: \n /запустить напоминания - это моя любимая! \n /удалить напоминания - для слабаков \n /наставник - запрос Базы данных \n /протереть стол|посуду|приборы - инструкции для начинающих профи \n /getid - посмотреть id-группы (чата)
    
    state: MainMenu || noContext = true
        # тег "q!" - (от англ. "question" — вопрос ) тег для паттернов
        # Это глобальный тег: переход по нему возможен из любого другого стейта в сценарии.
        q!: * (настав*/меню) *
        # тег "script" предназначен для написания js кода, который должен отработать в стейте
        script:
            # очищаем/дополнительно очищаем переменные шага и ошибок
            #$session.stepNumber = 0;
            $session.ErrorCounter = 0;
        # тег "a" предназначен для вывода сообщения пользователю
        a: Выбери пункт меню
        # тег "inlineButtons" выводит кнопки пользователю внутри сообщения
        # при нажатии кнопки пользоватлем в телеграм, срабатывает сообытие telegramCallbackQuery.
        # Чтобы отловить данное сообытие, использзуется тег "event!". 
        # Обработку этого сообытия, можно посмотреть в файле callback.sc
        # callback_data - сюда помещаются данные, которые придут в $request.rawRequest.callback_query.data при нажатии на эту кнопку
        inlineButtons:
            {text:"База знаний", callback_data: "knowledgeBase"}
#        buttons:
#            "База данных"
    
    # стейт для запуска чек-листа чистоты. при переходже по ссылке ввида: https://t.me/bot_name?start=cleaning-checklist
    state: WeekdayCheck
        # тег "q" - (от англ. "question" — вопрос ) тег для запросов пользователя (фраз, паттернов, интентов, сущностей) 
        # Это локальный тег: переход по нему возможен только из ближайшего родительского, из соседних или дочерних стейтов.
        # $regex - паттерн для обработки регулярных выражений. Подробнее тут: https://help.just-ai.com/docs/ru/Patterns/advanced_patterns#regexpregexp_i
        q: $regex</start cleaning-checklist>
        # тег "script" предназначен для написания js кода, который должен отработать в стейте
        script:
            # вызов функции подговки к запуску чек-листа
            # функция объявлена в файле utils.js
            # подключение файла utils.js осуществляется в requirements.sc
            prepareForChecklist();
        # тег "go!" осуществляет переход в указанный стейт и запускает все теги реакции перечисленные в нем.
        # тег "go" осуществляет переход в указанный стейт без запуска перечисленных в нем тегов реакций
        go!: /Checklist/Start
    
    state: KnowledgeBase || noContext = true
        q!: (база [знаний]) 
        # тег "a" предназначени для вывода сообщения пользователю
#        a: Ссылка на базу знаний: https://coda.io/d/_dgZS7-2eeoY/_suh9h#_luTiK
#        a: Ссылка на базу знаний: https://coda.io/d/CINNABON_doT76Y3ewFx/_surLS#_luKhB
        a: Ссылка на базу знаний: https://coda.io/d/_dMcDuoSN4e7/_suSE4#_luAfx
        # тег "inlineButtons" выводит кнопки пользователю внутри сообщения
        # при нажатии кнопки пользоватлем в телеграм, срабатывает сообытие telegramCallbackQuery.
        # Чтобы отловиить данное сообытие, использзуется тег "event!". 
        # Обработку этого сообытия, можно посмотреть в файле callback.sc
        # callback_data - сюда помещаются данные, которые придут в $request.rawRequest.callback_query.data при нажатии на эту кнопку
        inlineButtons:
            {text:"Вернуться к Чек-листу", callback_data: "repit_step"}

    # стейт для обработки события "Отправленный текст не распознан"
    state: NoMatch
        # Тег активации event! задает событие, по которому диалог может перейти в стейт.
        # Это глобальный тег: переход по нему возможен из любого другого стейта в сценарии.
        event!: noMatch
        # тег "a" предназначени для вывода сообщения пользователю
        # с помощью двойных фигурных скобок, можно подставлять js-выражения
        a: Я не понял. Вы сказали: {{$request.query}}
    
        # стейт для получения id_group (или id_user, если команда дана в личном чате с ботом)    
    state: getID
        q!: $regex</getid>
        script:
            if ($request.channelType.indexOf("chatwidget") > -1) {
                $reactions.answer("id чата: {{$request.channelUserId}}");
            } else {
                $reactions.answer("id чата: {{$request.data.chatId}}");
            }            

        # отладочный стейт, посмотреть, есть ли напоминание. если есть - отобразиться его номер    
    state: ViewRemind
        q!: $regex</view>
        script:
            $reactions.answer("Напоминание: {{$client.eventId}}");  

    # отладочный стейт Reset для сброса переменных, закрытия старой и открытия новой сессии
    state: Reset
        q!: $regex</reset>
        script: 
            $context.client = {};
            $jsapi.stopSession();
            $reactions.newSession({message: "/start", data: $request.data});           