let expireDateCalendar = document.getElementById("expireDateCalendar")
let generateKeyButton = document.getElementById("genKeyButton");
let serialFile = document.getElementById("fileChooseBtn");

expireDateCalendar.addEventListener('change', () => {
    generateKeyButton.disabled = !expireDateCalendar.value || serialFile.files.length == 0
})

serialFile.addEventListener('change', () => {
    generateKeyButton.disabled = !expireDateCalendar.value || serialFile.files.length == 0
})

let removeOrgBut = document.getElementById("removeOrgBut");
removeOrgBut.addEventListener('click', ()=>{
 let selectedOrg = document.getElementById("chosenOrganisation").value;
 document.getElementById("organisationForEdit").value = selectedOrg;
})

