package ru.prostak.messenger.ui.fragments.register

import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*
import ru.prostak.messenger.R
import ru.prostak.messenger.database.*
import ru.prostak.messenger.ui.fragments.BaseFragment
import ru.prostak.messenger.utilits.*

class EnterCodeFragment(val mPhoneNumber: String, val id: String) :
    BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.title = mPhoneNumber
        showSoftKeyboard(register_input_code)
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = it.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }
    /** Функция, вызывающаяся при вводе пользователем шестизначного кода*/
    private fun enterCode() {
        /** Сохранение кода в переменную*/
        val code = register_input_code.text.toString()
        /** Получение объекта credential - объекта с учётными данными,
         *  на основе id и кода (id был получен на этапе отправки кода, нужен для проверки)*/
        val credential = PhoneAuthProvider.getCredential(id, code)
        /** Попытка авторизации с полученными учётными данными*/
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                /** При успешной авторизации - получаем уникальный идентификатор пользователя,
                 *  записываем UID, телефон, username в базу данных*/
                val uid = AUTH.currentUser?.uid.toString()
                val dataMap = mutableMapOf<String, Any>()
                dataMap[CHILD_ID] = uid
                dataMap[CHILD_PHONE] = mPhoneNumber
                dataMap[CHILD_USERNAME] = uid
                /** Работа с базой данных Firebase (запись в БД номера телефона - UID в отдельную таблицу) */
                REF_DATABASE_ROOT.child(NODE_PHONES).child(mPhoneNumber).setValue(uid)
                    /** При ошибке записи в БД выводится ошибка */
                    .addOnFailureListener { showToast(it.message.toString()) }
                    /** При успешной записи в БД номера-ID происходит запись всех
                     *  остальных данных пользователя в другую таблицу. В свою очередь
                     *  при успешной записи этих данных происходит переход на главную страницу приложения*/
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dataMap)
                            .addOnSuccessListener {
                                showToast("Добро пожаловать")
                                restartActivity()
                            }
                            .addOnFailureListener { showToast(it.message.toString()) }
                    }
            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }
}