export async function getAddress({ latitude, longitude }) {
    const { data } = await fetch(
        `https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${latitude}&longitude=${longitude}`
    );
    if (!data) throw Error("Failed getting address");

    return data;
}