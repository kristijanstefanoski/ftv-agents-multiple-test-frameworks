const fs = require("fs");
const { exec } = require("child_process");

exec(`date +"%y%m%d_%H%M"`, (error, stdout, stderr) => {
  let parametersEnv = fs
    .readFileSync(`${__dirname}/../parameters_rerun.env`)
    .toString();
  parametersEnv = parametersEnv.replace(
    new RegExp(/(?<=SL_BUILD_NAME=).*/gm),
    `build_${stdout.replace(/[\n\t\r]/g, "")}_rerun`
  );
  fs.writeFileSync(`${__dirname}/../parameters_rerun.env`, parametersEnv);
});
