     
class clockType
{
public:
    void setTime(int hours, int minutes, int seconds);
    void getTime(int& hours, int& minutes, int& seconds) const;
    void printTime() const;

    void incrementSeconds();
    void incrementMinutes();
    void incrementHours();

    bool equalTime(const clockType& otherClock) const;

    int getHours() const
    {
        return hr;
    }

    clockType(int hours = 0, int minutes = 0, int seconds = 0);

private:
    int hr; 
    int min; 
    int sec;
};
