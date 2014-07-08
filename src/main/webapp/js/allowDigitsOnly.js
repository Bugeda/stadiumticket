$('#price_1, #price_2, #price_3, #price_4, #price_5, #price_6, #price_7, #price_8, ' +
    '#price_9, #price_10, #price_11, #price_12, #price_13, #price_14, #price_15, #price_16, ' +
    '#price_17, #price_18, #price_19, #price_20, #price_21, #price_22, #price_23, #price_24, ' +
    '#price_25, #price_26, #price_27').numeric({
    allowPlus: false,  // Allow the + sign
    allowMinus: false,  // Allow the - sign
    allowThouSep: false,  // Allow the thousands separator, default is the comma eg 12,000
    allowDecSep: true,  // Allow the decimal separator, default is the fullstop eg 3.141
    allowLeadingSpaces: false,
    maxDigits: 5,     // The max number of digits
    maxDecimalPlaces: 2,   // The max number of decimal places
    maxPreDecimalPlaces: 3,   // The max number digits before the decimal point
    max: NaN,   // The max numeric value allowed
    min: NaN    // The min numeric value allowed
});