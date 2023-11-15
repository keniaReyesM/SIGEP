import Vue from 'vue';
import {
    required,
    max,
    min,
    email,
    numeric,
    alpha_num,
    confirmed,
    between,
    min_value,
    max_value,
    regex,
    alpha,
    length,
} from "vee-validate/dist/rules";

import {
    ValidationObserver,
    ValidationProvider,
    extend,
    setInteractionMode,
} from "vee-validate";
setInteractionMode("eager");


Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);


extend("decimal", {
    validate: (value, {
        decimals = '*',
        separator = '.'
    } = {}) => {
        if (value === null || value === undefined || value === '') {
            return {
                valid: false
            };
        }
        if (Number(decimals) === 0) {
            return {
                valid: /^-?\d*$/.test(value),
            };
        }
        const regexPart = decimals === '*' ? '+' : `{1,${decimals}}`;
        const regex = new RegExp(`^[-+]?\\d*(\\${separator}\\d${regexPart})?([eE]{1}[-]?\\d+)?$`);

        return {
            valid: regex.test(value),
        };
    },
    message: '{_field_} debe ser un valor decimal'
});

extend("max_value", {
    ...max_value,
    message: "{_field_} valor fuera del rango permitido",
});

extend("min_value", {
    ...min_value,
    message: "{_field_} valor fuera del rango permitido",
});

extend("between", {
    ...between,
    message: "{_field_} valor fuera del rango permitido",
});

extend("required", {
    ...required,
    message: "{_field_} no puede quedar vacío",
});

extend("confirmed", {
    ...confirmed,
    message: "{_field_} no coincide.",
});

extend("max", {
    ...max,
    message: "{_field_} no debe ser mayor de {length} caracteres",
});

extend("min", {
    ...min,
    message: "{_field_} no debe ser menor de {length} caracteres",
});

extend("email", {
    ...email,
    message: "{_field_} incorrecto, ej: juanperez@ejemplo.com",
});

extend("numeric", {
    ...numeric,
    message: "{_field_} solo debe contener valores numéricos",
});

extend("alpha_num", {
    ...alpha_num,
    message: "{_field_} solo debe contener letras y números",
});

extend("alpha", {
    ...alpha,
    message: "{_field_} solo debe contener letras",
});

extend("regex", {
    ...regex,
    message: "{_field_} no debe contener caracteres especiales o espacios",
});

extend("length", {
    ...length,
    message: "{_field_} no debe ser mayor o menor a {length} digitos",
});
