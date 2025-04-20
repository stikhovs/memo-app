package com.sergio.memo_app.util;

import com.sergio.memo_app.persistence.dto.constant.CategoryConstant;

public final class ConstantHelper {

    public static class User {
        public static final String USERNAME_1 = "test user";
        public static final String EMAIL_1 = "test@test.com";
        public static final Integer USER_ID_1 = 1;
        public static final String USERNAME_2 = "John Wick";
        public static final String EMAIL_2 = "john@wick.com";
        public static final Integer USER_ID_2 = 2;
        public static final String USERNAME_3 = "John McClane";
        public static final String EMAIL_3 = "john@mcclane.com";
    }

    public static class CardSet {
        public static final Long CARD_SET_ID_1 = 1L;
        public static final Long CARD_SET_ID_2 = 2L;
        public static final String CARD_SET_TITLE_1 = "test card set";
        public static final String CARD_SET_TITLE_2 = "card set title 2";
        public static final String CARD_SET_UUID_1 = "fa89840d-44ba-455e-91ce-589f3b3a7b24";
    }

    public static class Card {
        public static Long getId(int num) {
            return (long) num;
        }

        public static String getFrontSide(int num) {
            return "front " + num;
        }

        public static String getBackSide(int num) {
            return "back " + num;
        }
    }

    public static class Category {
        public static Long CATEGORY_ID_1 = 1L;
        public static Long CATEGORY_ID_2 = 2L;
        public static String CATEGORY_TITLE_1 = CategoryConstant.DEFAULT_CATEGORY;
        public static String CATEGORY_TITLE_2 = "test category";
    }

    public static class Telegram {
        public static Long TELEGRAM_USER_ID_1 = 123L;
        public static Long TELEGRAM_CHAT_ID_1 = 456L;
        public static String TELEGRAM_USERNAME_1 = "test telegram user";
        public static Long TELEGRAM_USER_ID_2 = 444L;
        public static Long TELEGRAM_CHAT_ID_2 = 777L;
        public static String TELEGRAM_USERNAME_2 = "test";
        public static Long TELEGRAM_USER_ID_3 = 555L;
        public static Long TELEGRAM_CHAT_ID_3 = 666L;
        public static String TELEGRAM_USERNAME_3 = "test 123";
    }

    public static class Header {
        public static String X_INTERNAL_AUTH_KEY = "X-Internal-Auth";
        public static String X_INTERNAL_AUTH_VALUE = "test-key";
    }
}
