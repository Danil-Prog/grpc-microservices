# mobile-application

## Documentation

Spring profile

## Deploy architecture

VM contain instances with profile [develop] and [production]

```shell
docker images
```
Result:

| Image name      | Type image | Tag           |
|-----------------|------------|---------------|
| psql-server     | database   | :postgres     |
| psql-server-dev | database   | :postgres-dev |
| mobile-backend  | kotlin     | :develop      |
| mobile-backend  | kotlin     | :production   |


## UML Diagram

[![](https://mermaid.ink/img/pako:eNplkU9vhCAQxb-KmVObrEnF3abh0EOz1556FA8DTFfjHwxik8b43YuyWJty4se8x4OZGZTRBBxka1STSnIo-sQvZdqp68ckD4hWFQLQVrUj5WpLAkp-r40DKoqgZVY8CLi-ZYELfjnnrBTwWB7VWVSzoGZRzfLz5Z_6OYDM5lmARNVQr9PBGh1dL0_esyzx2j8Zkh1dmr5-Tattg6N5_0eSpq8-M2ZvqFfe3x4ULObcFQxO0JHtsNa-sfNaFeAq6nzTuN9qtI0A0S9eh5MzH9-9Au7sRCewZrpVwD-xHT1Ng0ZH1xpvFrv9lHTtjH0Pc9vGt_wAcWSE3Q?type=png)](https://mermaid.live/edit#pako:eNplkU9vhCAQxb-KmVObrEnF3abh0EOz1556FA8DTFfjHwxik8b43YuyWJty4se8x4OZGZTRBBxka1STSnIo-sQvZdqp68ckD4hWFQLQVrUj5WpLAkp-r40DKoqgZVY8CLi-ZYELfjnnrBTwWB7VWVSzoGZRzfLz5Z_6OYDM5lmARNVQr9PBGh1dL0_esyzx2j8Zkh1dmr5-Tattg6N5_0eSpq8-M2ZvqFfe3x4ULObcFQxO0JHtsNa-sfNaFeAq6nzTuN9qtI0A0S9eh5MzH9-9Au7sRCewZrpVwD-xHT1Ng0ZH1xpvFrv9lHTtjH0Pc9vGt_wAcWSE3Q)